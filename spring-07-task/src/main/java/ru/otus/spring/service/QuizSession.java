package ru.otus.spring.service;

import lombok.Data;
import ru.otus.spring.domain.Student;

@Data
public class QuizSession {

    Student student;

    double score = -1;

    boolean isLoggedIn() {
        return student != null;
    }

    boolean isQuizFinished() {
        return score != -1;
    }

    public void clear() {
        student = null;
        score = -1;
    }
}
