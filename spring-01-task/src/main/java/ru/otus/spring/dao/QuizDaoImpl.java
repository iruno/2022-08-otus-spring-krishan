package ru.otus.spring.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import ru.otus.spring.domain.Question;

public class QuizDaoImpl implements QuizDao {

    private final String resourceFileName;

    public QuizDaoImpl(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    private List<Question> questionList;

    @Override
    public List<Question> findAll() {
        if (questionList == null) {
            InputStream stream = this.getClass().getResourceAsStream(resourceFileName);
            Reader targetReader = new InputStreamReader(stream);
            questionList = new CsvToBeanBuilder(targetReader).withType(Question.class).build().parse();
        }

        return questionList;
    }
}
