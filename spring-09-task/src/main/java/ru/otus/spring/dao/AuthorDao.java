package ru.otus.spring.dao;

import java.util.List;
import ru.otus.spring.domain.Author;

public interface AuthorDao {

    long count();

    long insert(Author author);

    int update(Author author);

    Author getById(long id);

    List<Author> getAll();

    int deleteById(long id);
}
