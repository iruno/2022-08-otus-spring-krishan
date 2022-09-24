package ru.otus.spring.service;

import java.util.List;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Question;

public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public List<Question> findAll() {
        return quizDao.findAll();
    }
}
