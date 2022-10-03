package ru.otus.spring.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ReaderProviderCsv implements ReaderProvider {

    private final String resourceFileName;

    public ReaderProviderCsv(@Value("${quiz.resource-file-name}") String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    @Override
    public Reader get() {
        InputStream stream = this.getClass().getResourceAsStream(resourceFileName);
        return new InputStreamReader(stream);
    }
}