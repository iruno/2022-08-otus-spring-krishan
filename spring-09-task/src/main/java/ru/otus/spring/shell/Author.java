package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.AuthorService;

@ShellComponent
@RequiredArgsConstructor
public class Author {

    private final AuthorService authorService;

    @ShellMethod(value = "Create author", key = {"create author"})
    public void create() {
        authorService.create();
    }

    @ShellMethod(value = "Get all authors", key = {"list author", "get all authors"})
    public void getAll() {
        authorService.printAll();
    }

    @ShellMethod(value = "Get author", key = {"get author"})
    public void get() {
        authorService.get();
    }

    @ShellMethod(value = "Update author", key = {"update author"})
    public void update() {
        authorService.update();
    }

    @ShellMethod(value = "Count author", key = {"count author"})
    public void count() {
        authorService.count();
    }

    @ShellMethod(value = "Delete author", key = {"delete author"})
    public void delete() {
        authorService.delete();
    }
}
