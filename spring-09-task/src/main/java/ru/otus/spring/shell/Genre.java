package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class Genre {
    private final GenreService genreService;

    @ShellMethod(value = "Create genre", key = {"create genre"})
    public void create() {
        genreService.create();
    }

    @ShellMethod(value = "Get all genres", key = {"list genre", "get all genres"})
    public void getAll() {
        genreService.printAll();
    }

    @ShellMethod(value = "Get genre", key = {"get genre"})
    public void get() {
        genreService.get();
    }

    @ShellMethod(value = "Update genre", key = {"update genre"})
    public void update() {
        genreService.update();
    }

    @ShellMethod(value = "Count genre", key = {"count genre"})
    public void count() {
        genreService.count();
    }

    @ShellMethod(value = "Delete genre", key = {"delete genre"})
    public void delete() {
        genreService.delete();
    }
}
