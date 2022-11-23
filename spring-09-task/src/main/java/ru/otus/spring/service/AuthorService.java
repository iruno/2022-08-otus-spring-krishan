package ru.otus.spring.service;

import java.util.List;
import ru.otus.spring.domain.Author;

public interface AuthorService {
    Author create();

    Author getById(long id);

    Author get();

    List<Author> printAll();

    Author update();

    long count();

    int delete();
}
