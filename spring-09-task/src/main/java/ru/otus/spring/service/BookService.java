package ru.otus.spring.service;


import java.util.List;
import ru.otus.spring.domain.Book;

public interface BookService {

    Book create();

    Book getById(long id);

    Book get();

    List<Book> getAll();

    Book update();

    long count();

    int delete();
}
