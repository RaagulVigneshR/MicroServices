package org.bridgelabz.quizquestionservices.service;

import org.bridgelabz.quizquestionservices.model.QuestionsModel;
import org.bridgelabz.quizquestionservices.model.Response;
import org.bridgelabz.quizquestionservices.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class QuestionService implements iQuestionService{

    @Autowired
    QuestionsRepository questionRepository;

    @Override
    public Mono<ResponseEntity<Response>> create(QuestionsModel question) {
        return questionRepository.save(question).map(createdLabel -> new ResponseEntity<>(new Response(200, "question created successfully"), HttpStatus.OK))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<ResponseEntity<? extends Object>> getAll() {
        return questionRepository.findAll()
                .collectList()
                .flatMap(questions -> {
                    if (!questions.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(Flux.fromIterable(questions), HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }
                });
    }

    @Override
    public Mono<ResponseEntity<Object>> getById(long id) {
        return questionRepository.existsById(id)
                .flatMap(exist -> {
                    if (exist) {
                        return Mono.just(new ResponseEntity<>(questionRepository.findById(id), HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }
                });
    }

    @Override
    public Mono<ResponseEntity<Response>> update(long id, QuestionsModel question) {
        return questionRepository.findById(id)
                .flatMap(existing -> {
                    existing.setQuestion(question.getQuestion());
                    return questionRepository.save(existing) .map(updated -> new ResponseEntity<>(new Response(200, "question updated successfully", updated), HttpStatus.OK))
                            .defaultIfEmpty(new ResponseEntity<>(new Response(404, "question not found"), HttpStatus.NOT_FOUND))
                            .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

                });
    }

    @Override
    public Mono<ResponseEntity<Response>> delete(long id) {
        return questionRepository.existsById(id)
                .flatMap(exist->{
                    if(exist){
                        return Mono.just(new ResponseEntity<>(new Response(204, "question deleted successfully"), HttpStatus.OK));
                    }else{
                        return Mono.just(new ResponseEntity<>(new Response(404, "question not found"), HttpStatus.NOT_FOUND));
                    }
                })
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));

    }

    @Override
    public Mono<ResponseEntity<? extends Object>> getAllByQuizId(long quizId) {
        return questionRepository.findByQuizId(quizId)
                .collectList()
                .flatMap(questions -> {
                    if (!questions.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(Flux.fromIterable(questions), HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }
                });
    }




}
