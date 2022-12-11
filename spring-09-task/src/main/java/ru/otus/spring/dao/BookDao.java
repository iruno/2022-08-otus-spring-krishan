package ru.otus.spring.dao;

import java.util.List;
import ru.otus.spring.domain.Book;

public interface BookDao {

    long count();

    long insert(Book book);

    int updateTitle(Book book);

    Book getById(long id);

    List<Book> getAll();

    int deleteById(long id);
}
