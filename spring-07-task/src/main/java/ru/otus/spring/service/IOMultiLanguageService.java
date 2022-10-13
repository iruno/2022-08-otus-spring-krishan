package ru.otus.spring.service;

public interface IOMultiLanguageService {
    void out(String code, Object... args);
    String readLn(String code, Object... args);
    int readInt(String code, Object... args);
    void write(String message);
}
