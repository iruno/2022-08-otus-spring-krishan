package ru.otus.spring.service;

public interface IOService {
    void out(String message);
    String readLn(String prompt);
    int readInt(String prompt);
}
