package org.bridgelabz.quizservice.service;

import org.bridgelabz.quizservice.model.Question;
import org.bridgelabz.quizservice.model.QuizModel;
import org.bridgelabz.quizservice.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface iQuizService {
    Mono<ResponseEntity<Response>> create(QuizModel quizModel);
    Mono<ResponseEntity<?extends Object>> getAll();
    Mono<ResponseEntity<Object>> getById(Long id);
    Mono<ResponseEntity<Response>> update(Long id,QuizModel quizModel);
    Mono<ResponseEntity<Response>> delete(Long id);
    Flux<Question> fetchQuestions(Long quizId);
}
