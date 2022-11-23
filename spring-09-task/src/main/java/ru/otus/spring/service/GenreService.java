package ru.otus.spring.service;


import java.util.List;
import ru.otus.spring.domain.Genre;

public interface GenreService {
    Genre create();

    Genre getById(long id);

    Genre get();

    List<Genre> printAll();

    Genre update();

    long count();

    int delete();
}
