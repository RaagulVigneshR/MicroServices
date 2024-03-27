package org.bridgelabz.quizservice.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("quiz")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizModel {
    @Id
    private Long id;
    private String name;
}
