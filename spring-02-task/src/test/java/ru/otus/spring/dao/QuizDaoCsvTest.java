package ru.otus.spring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ReaderProvider;

@ExtendWith(MockitoExtension.class)
public class QuizDaoCsvTest {

    @Mock
    private ReaderProvider readerProvider;

    private QuizDao quizDao;

    @BeforeEach
    void setUp() {
        quizDao = new QuizDaoImpl(readerProvider);
    }

    @DisplayName("Check if quiz parsing is working fine")
    @Test
    public void questionsReadOk() {
        when(readerProvider.get()).thenReturn(new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream("/quiz_test1.csv"))));
        List<Question> questions = quizDao.findAll();
        assertEquals(3, questions.size());
        Question question = questions.get(0);
        assertEquals("2+2", question.getQuestion());
        List<String> answers = question.getAnswers();
        assertEquals(3, answers.size());
        assertEquals("1", answers.get(0));
        assertEquals(0, question.getRightChoice());
    }
}
