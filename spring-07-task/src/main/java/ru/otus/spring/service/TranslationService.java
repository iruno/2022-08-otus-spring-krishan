package ru.otus.spring.service;

import org.springframework.context.NoSuchMessageException;

public interface TranslationService {
    String translate(String code, Object... args) throws NoSuchMessageException;
}
