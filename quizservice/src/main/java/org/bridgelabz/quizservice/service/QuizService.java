package org.bridgelabz.quizservice.service;

import org.bridgelabz.quizservice.model.Question;
import org.bridgelabz.quizservice.model.QuizModel;
import org.bridgelabz.quizservice.model.Response;
import org.bridgelabz.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class QuizService implements iQuizService {

    private final QuizRepository quizRepository;
    @Autowired
    QuestionClient questionClient;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public Mono<ResponseEntity<Response>> create(QuizModel quizModel) {
        return quizRepository.save(quizModel)
                .map(savedQuiz -> new ResponseEntity<>(new Response(HttpStatus.CREATED.value(), "Quiz created successfully", savedQuiz), HttpStatus.CREATED))
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }


    @Override
    public Mono<ResponseEntity<? extends Object>> getAll() {
        return Mono.just(ResponseEntity.ok(quizRepository.findAll()));
    }

    @Override
    public Mono<ResponseEntity<Object>> getById(Long id) {
        return quizRepository.findById(id)
                .map(quizModel -> (Object) quizModel) // Cast QuizModel to Object
                .map(ResponseEntity::ok) // Wrap the QuizModel in ResponseEntity
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Handle empty result
    }


    @Override
    public Mono<ResponseEntity<Response>> update(Long id, QuizModel quizModel) {
        return quizRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        quizModel.setId(id); // Ensure the correct ID is set
                        quizRepository.save(quizModel);
                        Response response = new Response(HttpStatus.OK.value(), "Quiz updated successfully", quizModel);
                        return Mono.just(ResponseEntity.ok(response));
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    @Override
    public Mono<ResponseEntity<Response>> delete(Long id) {
        return quizRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        quizRepository.deleteById(id);
                        Response response = new Response(HttpStatus.OK.value(), "Quiz deleted successfully");
                        return Mono.just(ResponseEntity.ok(response));
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
    @Override
    public Flux<Question> fetchQuestions(Long quizId) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http") // Add the scheme
                        .host("localhost") // Add the host
                        .port(8081) // Add the port of questionservice
                        .path("/question/quiz/{quizId}")
                        .build(quizId))  // Pass quizId as a path variable
                .retrieve()
                .bodyToFlux(Question.class);
    }
    public Mono<ResponseEntity<? extends Object>> getQuestionsOfQuiz(long quizId) {
        return questionClient.getQuestionOfQuiz(quizId)
                .collectList()
                .flatMap(questions -> {
                    if (!questions.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(Flux.fromIterable(questions), HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                    }
                })
                .onErrorResume(e -> Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));

    }
}
