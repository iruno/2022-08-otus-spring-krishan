package ru.otus.spring.service;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final MessageSource messageSource;
    private final Locale locale;

    public TranslationServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.locale = localeProvider.getLocale();
    }

    @Override
    public String translate(String code, Object[] args) {
        return this.messageSource.getMessage(code, args, locale);
    }
}
