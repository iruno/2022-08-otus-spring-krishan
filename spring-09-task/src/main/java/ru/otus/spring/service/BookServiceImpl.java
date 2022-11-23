package ru.otus.spring.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final IOMultiLanguageService io;

    @Override
    public Book create() {
        String title = io.readLn("enter_title");
        Genre genre = chooseGenre();
        Author author = chooseAuthor();
        Book book = Book.builder()
                .title(title)
                .genre(genre)
                .author(author)
                .build();
        long id = bookDao.insert(book);
        book.setId(id);
        io.out("book_added");
        print(book);
        return book;
    }

    private Genre chooseGenre() {
        genreService.printAll();
        long genreId = io.readLong("choose_genre");
        return genreService.getById(genreId);
    }

    private Author chooseAuthor() {
        authorService.printAll();
        long authorId = io.readLong("choose_author");
        return authorService.getById(authorId);
    }

    @Override
    public Book get() {
        Book book = getBookById();
        if (book != null) {
            print(book);
        }
        return book;
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        List<Book> result = bookDao.getAll();
        result.forEach(this::print);
        return result;
    }

    @Override
    public Book update() {
        Book book = getBookById();
        if (book != null) {
            book.setTitle(io.readLn("enter_title"));
            bookDao.updateTitle(book);
            io.out("done");
        }
        return book;
    }

    @Override
    public long count() {
        long count = bookDao.count();
        io.out("count", count);
        return count;
    }

    @Override
    public int delete() {
        Book book = getBookById();
        if (book != null) {
            int rowsAffected = bookDao.deleteById(book.getId());
            io.out("done");
            return rowsAffected;
        }
        return 0;
    }

    private Book getBookById() {
        return bookDao.getById(io.readLong("enter_id"));
    }

    private void print(Book book) {
        io.write(book.toString());
    }
}
