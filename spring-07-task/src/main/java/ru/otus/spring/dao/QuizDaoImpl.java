package ru.otus.spring.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.ReaderProvider;

@Component
@RequiredArgsConstructor
public class QuizDaoImpl implements QuizDao {

    private final ReaderProvider readerProvider;

    private List<Question> questionList;

    @Override
    public List<Question> findAll() {
        if (questionList == null) {
            questionList = new CsvToBeanBuilder(readerProvider.get()).withType(Question.class).build().parse();
        }

        return questionList;
    }
}
