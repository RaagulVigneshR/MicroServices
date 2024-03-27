package org.bridgelabz.quizquestionservices.service;

import org.bridgelabz.quizquestionservices.model.QuestionsModel;
import org.bridgelabz.quizquestionservices.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public interface iQuestionService {
    Mono<ResponseEntity<Response>> create(QuestionsModel question);
    Mono<ResponseEntity<? extends Object>> getAll();
    Mono<ResponseEntity<Object>> getById(long id);
    Mono<ResponseEntity<Response>>update(long id,QuestionsModel question);
    Mono<ResponseEntity<Response>> delete(long id);
    Mono<ResponseEntity<? extends Object>> getAllByQuizId(long quizId);
}
