package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IOMultiLanguageServiceImpl implements IOMultiLanguageService {

    private final IOService ioService;

    private final TranslationService translationService;

    @Override
    public void out(String code, Object... args) {
        ioService.out(getTranslatedMessage(code, args));
    }

    @Override
    public String readLn(String code, Object... args) {
        return ioService.readLn(getTranslatedMessage(code, args));
    }

    @Override
    public long readLong(String code, Object... args) {
        return ioService.readLong(getTranslatedMessage(code, args));
    }

    @Override
    public void write(String message) {
        ioService.out(message);
    }

    private String getTranslatedMessage(String code, Object... args) {
        return translationService.translate(code, args);
    }
}
