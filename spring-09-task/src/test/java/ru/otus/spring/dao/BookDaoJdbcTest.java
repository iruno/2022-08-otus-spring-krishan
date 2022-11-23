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
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void count() {
        assertEquals(5L, bookDaoJdbc.count());
    }

    @Test
    void insert() {
        Book book = Book.builder()
                .title("Title")
                .genre(new Genre(2L, "Poem"))
                .author(new Author(2L, "Alexander", "Pushkin"))
                .build();
        long id = bookDaoJdbc.insert(book);
        assertEquals(6L, id);
        Book bookById = bookDaoJdbc.getById(id);
        assertEquals(book.getTitle(), bookById.getTitle());
    }


    @Test
    void updateTitle() {
        Book book = new Book(3L, "14 wood mice and the Winter Sledding Day",
                new Genre(1L, "Fairy tale"),
                new Author(1L, "Kadzuo", "Iwamura"));
        bookDaoJdbc.updateTitle(book);
        Book bookById = bookDaoJdbc.getById(3L);
        assertEquals(book, bookById);
    }

    @Test
    void getById() {
        Book book = bookDaoJdbc.getById(1L);
        assertEquals("14 wood mice. Breakfast", book.getTitle());
        assertEquals(new Genre(1, "Fairy tale"), book.getGenre());
        assertEquals(new Author(1, "Kadzuo", "Iwamura"), book.getAuthor());
    }

    @Test
    @DisplayName("getById should throw EmptyResultDataAccessException if not found")
    void getByIdNotExists() {
        assertThatThrownBy(() -> bookDaoJdbc.getById(100500L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getAll() {
        List<Book> list = bookDaoJdbc.getAll();
        assertThat(list.size()).isEqualTo(5L);
        Genre genreFairyTale = new Genre(1, "Fairy tale");
        Author authorIwamura = new Author(1, "Kadzuo", "Iwamura");
        assertThat(list).contains(Book.builder()
                .id(1L)
                .title("14 wood mice. Breakfast")
                .genre(genreFairyTale)
                .author(authorIwamura)
                .build()
        );
        assertThat(list).contains(Book.builder()
                .id(2L)
                .title("14 wood mice. Picnic")
                .genre(genreFairyTale)
                .author(authorIwamura)
                .build()
        );
        assertThat(list).contains(Book.builder()
                .id(3L)
                .title("14 wood mice and the Winter Sledding Day")
                .genre(genreFairyTale)
                .author(authorIwamura)
                .build()
        );
        assertThat(list).contains(Book.builder()
                .id(4L)
                .title("Ruslan and Ludmila")
                .genre(new Genre(2, "Poem"))
                .author(new Author(2, "Alexander", "Pushkin"))
                .build()
        );
        assertThat(list).contains(Book.builder()
                .id(5L)
                .title("Snail and Kit")
                .genre(genreFairyTale)
                .author(new Author(3, "Julia", "Donaldson"))
                .build()
        );
    }

    @Test
    void deleteById() {
        long before = bookDaoJdbc.count();
        int rowsAffected = bookDaoJdbc.deleteById(3L);
        assertEquals(1, rowsAffected);
        long after = bookDaoJdbc.count();
        assertEquals(before - 1, after);
        assertThatThrownBy(() -> bookDaoJdbc.getById(3L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("deleteById should return affectedRows=0 if not found")
    void deleteByIdNotExists() {
        int rowsAffected = bookDaoJdbc.deleteById(100500L);
        assertEquals(0, rowsAffected);
    }
}
