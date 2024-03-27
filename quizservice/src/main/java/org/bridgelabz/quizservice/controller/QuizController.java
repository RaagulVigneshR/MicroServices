package org.bridgelabz.quizservice.controller;


import org.bridgelabz.quizservice.model.Question;
import org.bridgelabz.quizservice.model.QuizModel;
import org.bridgelabz.quizservice.model.Response;
import org.bridgelabz.quizservice.service.iQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    iQuizService quizService;
    @PostMapping("/Create")
    public Mono<ResponseEntity<Response>> createQuiz(@RequestBody QuizModel quizModel) {
        return quizService.create(quizModel);
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<? extends Object>> getAllQuizzes() {
        return quizService.getAll();
    }

    @GetMapping("/getbyid/{id}")
    public Mono<ResponseEntity<Object>> getQuizById(@PathVariable Long id) {
        return quizService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Response>> updateQuiz(@PathVariable Long id, @RequestBody QuizModel quizModel) {
        return quizService.update(id, quizModel);
    }
    @GetMapping("/question/{quizId}")  // Corrected endpoint
    public Flux<Question> getQuiz(@PathVariable Long quizId) {
        return quizService.fetchQuestions(quizId);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Response>> deleteQuiz(@PathVariable Long id) {
        return quizService.delete(id);
    }
}
