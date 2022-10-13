package ru.otus.spring.service;

import java.util.Locale;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class QuizConfigurationImpl implements QuizConfiguration, LocaleProvider {

    private final Double minScore;

    private Locale locale;

    public QuizConfigurationImpl(@Value("${quiz.min-score}") Double minScore, @Value("${quiz.locale}") String locale) {
        this.minScore = minScore;
        this.locale = new Locale(locale);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
