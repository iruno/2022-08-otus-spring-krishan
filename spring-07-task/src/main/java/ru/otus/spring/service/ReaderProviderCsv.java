package ru.otus.spring.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.ResourceFileNameProvider;

@Component
@AllArgsConstructor
public class ReaderProviderCsv implements ReaderProvider {

    private final ResourceFileNameProvider resourceFileNameProvider;

    @Override
    public Reader get() {
        InputStream stream = this.getClass().getResourceAsStream(resourceFileNameProvider.getResourceFileName());
        return new InputStreamReader(stream);
    }
}