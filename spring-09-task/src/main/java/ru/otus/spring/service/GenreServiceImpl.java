package ru.otus.spring.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final IOMultiLanguageService io;

    @Override
    public Genre create() {
        String name = io.readLn("enter_name");
        Genre genre = Genre.builder().name(name).build();
        long id = genreDao.insert(genre);
        genre.setId(id);
        io.out("genre_added");
        print(genre);
        return genre;
    }

    @Override
    public Genre get() {
        Genre genre = getGenreById();
        if (genre != null) {
            print(genre);
        }
        return genre;
    }

    @Override
    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public List<Genre> printAll() {
        List<Genre> result = genreDao.getAll();
        result.forEach(this::print);
        return result;
    }

    @Override
    public Genre update() {
        Genre genre = getGenreById();
        if (genre != null) {
            genre.setName(io.readLn("enter_name"));
            genreDao.update(genre);
            io.out("done");
        }
        return genre;
    }

    @Override
    public long count() {
        long count = genreDao.count();
        io.out("count", count);
        return count;
    }

    @Override
    public int delete() {
        Genre genre = getGenreById();
        if (genre != null) {
            int rowsAffected = genreDao.deleteById(genre.getId());
            io.out("done");
            return rowsAffected;
        }
        return 0;
    }

    private Genre getGenreById() {
        return genreDao.getById(io.readLong("enter_id"));
    }

    private void print(Genre genre) {
        io.write(genre.toString());
    }
}
