package ru.otus.spring.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class QuizConfigurationImpl implements QuizConfiguration {

    private final Double minScore;

    public QuizConfigurationImpl(@Value("${quiz.min-score}") Double minScore) {
        this.minScore = minScore;
    }
}
