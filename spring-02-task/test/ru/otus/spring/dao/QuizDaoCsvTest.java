package ru.otus.homeWork.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.otus.homeWork.domain.Question;
import ru.otus.homeWork.service.ReaderServiceCsvImpl;

import java.util.List;

public class QuizDaoCsvTest {

    private final QuizDaoCsv questionDaoCsv = new QuizDaoCsv("/testData.csv"));

    @Test
    public void questionsReadOk() {
        List<Question> allQuestions = questionDaoCsv.findAll();
        Assert.assertTrue(allQuestions.size() == 3);
    }
}