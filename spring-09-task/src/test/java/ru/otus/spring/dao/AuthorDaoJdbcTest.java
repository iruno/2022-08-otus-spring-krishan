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
import ru.otus.spring.domain.Author;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void count() {
        assertEquals(authorDaoJdbc.count(), 4L);
    }

    @Test
    void insert() {
        Author author = new Author(-1L, "name", "surname");
        long id = authorDaoJdbc.insert(author);
        assertEquals(id, 5L);
        Author authorById = authorDaoJdbc.getById(id);
        assertEquals(author.getSurname(), authorById.getSurname());
        assertEquals(author.getName(), authorById.getName());
    }

    @Test
    @DisplayName("insert should throw DuplicateKeyException if already exists")
    void insertDuplicate() {
        Author author = new Author(-1L, "Kadzuo", "Iwamura");
        assertThatThrownBy(() -> authorDaoJdbc.insert(author))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void update() {
        Author author = new Author(4L, "Sergei", "Esenin");
        authorDaoJdbc.update(author);
        Author authorById = authorDaoJdbc.getById(4L);
        assertEquals(author, authorById);
    }

    @Test
    void getById() {
        Author author = authorDaoJdbc.getById(4L);
        assertEquals("Sergey", author.getName());
        assertEquals("Esenin", author.getSurname());
    }

    @Test
    @DisplayName("getById should throw EmptyResultDataAccessException if not found")
    void getByIdNotExists() {
        assertThatThrownBy(() -> authorDaoJdbc.getById(100500L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getAll() {
        List<Author> list = authorDaoJdbc.getAll();
        assertThat(list.size()).isEqualTo(4L);
        assertThat(list).contains(new Author(1L, "Kadzuo", "Iwamura"));
        assertThat(list).contains(new Author(2L, "Alexander", "Pushkin"));
        assertThat(list).contains(new Author(3L, "Julia", "Donaldson"));
        assertThat(list).contains(new Author(4L, "Sergey", "Esenin"));
    }

    @Test
    void deleteById() {
        long before = authorDaoJdbc.count();
        int rowsAffected = authorDaoJdbc.deleteById(4L);
        assertEquals(1, rowsAffected);
        long after = authorDaoJdbc.count();
        assertEquals(before - 1, after);
        assertThatThrownBy(() -> authorDaoJdbc.getById(4L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("deleteById should return affectedRows=0 if not found")
    void deleteByIdNotExists() {
        int rowsAffected = authorDaoJdbc.deleteById(100500L);
        assertEquals(0, rowsAffected);
    }
}
