package ru.otus.spring.config;

import java.util.Locale;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quiz")
@Data
public class QuizProperties implements QuizConfiguration, LocaleProvider, ResourceFileNameProvider {

    @Value("${quiz.min-score:50}")
    private Double minScore;

    private Locale locale;

    private String resourceFileName;
}
