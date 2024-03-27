package org.bridgelabz.quizservice.service;
import org.bridgelabz.quizservice.model.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@FeignClient(url="http://localhost:8081",value = "Question-Client")
public interface QuestionClient {

    @GetMapping("question/quiz/{quizId}")
    Flux<Question>getQuestionOfQuiz(@PathVariable long quizId);
}
