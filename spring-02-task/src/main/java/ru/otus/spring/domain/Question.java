package ru.otus.spring.domain;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import java.util.List;
import lombok.Data;
@Data
public class Question {
    @CsvBindByName(column = "id")
    Integer id;
    @CsvBindByName(column = "question", required = true)
    String question;
    @CsvBindByName(column = "rightChoice", required = true)
    Integer rightChoice;
    @CsvBindAndSplitByName(column = "answers", required = true, elementType = String.class, splitOn = "\\|")
    List<String> answers;
}
