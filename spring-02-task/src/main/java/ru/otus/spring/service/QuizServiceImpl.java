package ru.otus.spring.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Question;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;

    @Override
    public List<Question> findAll() {
        return quizDao.findAll();
    }
}
