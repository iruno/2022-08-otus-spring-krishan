package ru.otus.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private IOMultiLanguageService io;

    private final Long BOOK_ID = 100L;
    private final String BOOK_TITLE = "BOOK_TITLE";
    private final Genre GENRE = new Genre(1, "Fairy tale");
    private final Author AUTHOR = new Author(1, "Kadzuo", "Iwamura");

    private final Book BOOK = Book.builder()
            .title(BOOK_TITLE)
            .genre(GENRE)
            .author(AUTHOR)
            .build();

    private final Book BOOK_WITH_ID = Book.builder()
            .id(BOOK_ID)
            .title(BOOK_TITLE)
            .genre(GENRE)
            .author(AUTHOR)
            .build();

    @Test
    void create() {
        when(io.readLn("enter_title")).thenReturn(BOOK_TITLE);
        when(io.readLong("choose_genre")).thenReturn(GENRE.getId());
        when(genreService.getById(GENRE.getId())).thenReturn(GENRE);
        when(io.readLong("choose_author")).thenReturn(AUTHOR.getId());
        when(authorService.getById(AUTHOR.getId())).thenReturn(AUTHOR);
        when(bookDao.insert(BOOK)).thenReturn(BOOK_WITH_ID.getId());
        when(bookDao.getById(BOOK_WITH_ID.getId())).thenReturn(BOOK_WITH_ID);

        Book bookCreated = bookService.create();

        assertEquals(BOOK_WITH_ID, bookCreated);
    }

    @Test
    void get() {
        when(io.readLong("enter_id")).thenReturn(BOOK_WITH_ID.getId());
        when(bookDao.getById(BOOK_WITH_ID.getId())).thenReturn(BOOK_WITH_ID);

        Book bookById = bookService.get();

        assertEquals(BOOK_WITH_ID, bookById);
    }

    @Test
    void getById() {
        when(bookDao.getById(BOOK_WITH_ID.getId())).thenReturn(BOOK_WITH_ID);

        Book bookById = bookService.getById(BOOK_WITH_ID.getId());

        assertEquals(BOOK_WITH_ID, bookById);
    }

    @Test
    void getAll() {
        when(bookDao.getAll()).thenReturn(Collections.singletonList(BOOK_WITH_ID));

        List<Book> bookList = bookService.getAll();

        assertEquals(1, bookList.size());
        assertEquals(BOOK_WITH_ID, bookList.get(0));
    }

    @Test
    void update() {
        Book newBook = Book.builder()
                .id(BOOK_ID)
                .title("NEW_BOOK_TITLE")
                .genre(GENRE)
                .author(AUTHOR)
                .build();

        when(io.readLong("enter_id")).thenReturn(BOOK_WITH_ID.getId());
        when(bookDao.getById(BOOK_WITH_ID.getId())).thenReturn(BOOK_WITH_ID);
        when(io.readLn("enter_title")).thenReturn(newBook.getTitle());
        when(bookDao.updateTitle(newBook)).thenReturn(1 /* rowsAffected */);

        Book bookUpdated = bookService.update();

        assertEquals(newBook, bookUpdated);
    }

    @Test
    void count() {
        long expectedCount = 100500L;
        when(bookDao.count()).thenReturn(expectedCount);

        long actualCount = bookService.count();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void delete() {
        when(io.readLong("enter_id")).thenReturn(BOOK_WITH_ID.getId());
        when(bookDao.getById(BOOK_WITH_ID.getId())).thenReturn(BOOK_WITH_ID);
        when(bookDao.deleteById(BOOK_WITH_ID.getId())).thenReturn(1 /* rowsAffected */);

        int rowsAffected = bookService.delete();

        assertEquals(1, rowsAffected);
    }
}