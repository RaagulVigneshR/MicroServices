package org.bridgelabz.quizquestionservices.repository;

import org.bridgelabz.quizquestionservices.model.QuestionsModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface QuestionsRepository extends R2dbcRepository<QuestionsModel,Long> {


    Flux<QuestionsModel> findByQuizId(long quizId);
}
