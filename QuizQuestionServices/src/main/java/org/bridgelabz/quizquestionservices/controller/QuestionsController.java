package org.bridgelabz.quizquestionservices.controller;

import org.bridgelabz.quizquestionservices.model.QuestionsModel;
import org.bridgelabz.quizquestionservices.model.Response;
import org.bridgelabz.quizquestionservices.service.QuestionService;
import org.bridgelabz.quizquestionservices.service.iQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/question")
public class QuestionsController {


    @Autowired
    QuestionService iService;

    @PostMapping
    public Mono<ResponseEntity<Response>> create(@RequestBody QuestionsModel question){
        return iService.create(question);
    }

    @GetMapping
    public Mono<ResponseEntity<? extends Object>>getAll(){
        return iService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Object>> getById(@PathVariable long id){
        return iService.getById(id);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Response>> update(@PathVariable long id,@RequestBody QuestionsModel question){
        return iService.update(id,question);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> delete(@PathVariable long id){
        return iService.delete(id);
    }
    
    @GetMapping("/quiz/{quizId}")
    public Mono<ResponseEntity<? extends Object>>getAllByQuizId(@PathVariable long quizId){
        return iService.getAllByQuizId(quizId);
    }
}