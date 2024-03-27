package org.bridgelabz.quizservice.repository;

import org.bridgelabz.quizservice.model.QuizModel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends R2dbcRepository<QuizModel,Long> {

}
