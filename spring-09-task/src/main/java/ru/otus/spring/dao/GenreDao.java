package ru.otus.spring.dao;

import java.util.List;
import ru.otus.spring.domain.Genre;

public interface GenreDao {

    long count();

    long insert(Genre genre);

    int update(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    int deleteById(long id);
}
