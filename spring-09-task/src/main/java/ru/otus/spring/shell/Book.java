package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.BookService;

@ShellComponent
@RequiredArgsConstructor
public class Book {

    private final BookService bookService;

    @ShellMethod(value = "Create book", key = {"create book"})
    public void create() {
        bookService.create();
    }

    @ShellMethod(value = "Get all books", key = {"list book", "get all books"})
    public void getAll() {
        bookService.getAll();
    }

    @ShellMethod(value = "Get book", key = {"get book"})
    public void get() {
        bookService.get();
    }

    @ShellMethod(value = "Update book", key = {"update book"})
    public void update() {
        bookService.update();
    }

    @ShellMethod(value = "Count book", key = {"count book"})
    public void count() {
        bookService.count();
    }

    @ShellMethod(value = "Delete book", key = {"delete book"})
    public void delete() {
        bookService.delete();
    }
}
