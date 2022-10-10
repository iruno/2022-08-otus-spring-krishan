package ru.otus.spring.dao;

import java.util.List;
import ru.otus.spring.domain.Question;

public interface QuizDao {

    List<Question> findAll();
}
