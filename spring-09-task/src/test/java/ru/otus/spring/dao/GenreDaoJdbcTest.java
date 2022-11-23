package ru.otus.spring.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void count() {
        assertEquals(genreDaoJdbc.count(), 3L);
    }

    @Test
    void insert() {
        Genre genre = new Genre(-1L, "name");
        long id = genreDaoJdbc.insert(genre);
        assertEquals(4L, id);
        Genre genreById = genreDaoJdbc.getById(id);
        assertEquals(genre.getName(), genreById.getName());
    }

    @Test
    @DisplayName("insert should throw DuplicateKeyException if already exists")
    void insertDuplicate() {
        Genre genre = new Genre(-1L, "Poem");
        assertThatThrownBy(() -> genreDaoJdbc.insert(genre))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void update() {
        Genre genre = new Genre(3L, "Poetry");
        genreDaoJdbc.update(genre);
        Genre genreById = genreDaoJdbc.getById(3L);
        assertEquals(genre, genreById);
    }

    @Test
    void getById() {
        Genre genre = genreDaoJdbc.getById(1L);
        assertEquals("Fairy tale", genre.getName());
    }

    @Test
    @DisplayName("getById should throw EmptyResultDataAccessException if not found")
    void getByIdNotExists() {
        assertThatThrownBy(() -> genreDaoJdbc.getById(100500L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getAll() {
        List<Genre> list = genreDaoJdbc.getAll();
        assertEquals(3L, list.size());
        assertThat(list).contains(new Genre(1L, "Fairy tale"));
        assertThat(list).contains(new Genre(2L, "Poem"));
        assertThat(list).contains(new Genre(3L, "Horror"));
    }

    @Test
    void deleteById() {
        long before = genreDaoJdbc.count();
        int rowsAffected = genreDaoJdbc.deleteById(3L);
        assertEquals(1, rowsAffected);
        long after = genreDaoJdbc.count();
        assertEquals(before - 1, after);
        assertThatThrownBy(() -> genreDaoJdbc.getById(3L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("deleteById should return affectedRows=0 if not found")
    void deleteByIdNotExists() {
        int rowsAffected = genreDaoJdbc.deleteById(100500L);
        assertEquals(0, rowsAffected);
    }
}
