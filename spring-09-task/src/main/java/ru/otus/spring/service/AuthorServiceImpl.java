package ru.otus.spring.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final IOMultiLanguageService io;

    @Override
    public Author create() {
        String name = io.readLn("enter_name");
        String surName = io.readLn("enter_surname");
        Author author = Author.builder().name(name).surname(surName).build();
        long id = authorDao.insert(author);
        author.setId(id);
        io.out("author_added");
        print(author);
        return author;
    }

    @Override
    public Author get() {
        Author author = getAuthorById();
        if (author != null) {
            print(author);
        }
        return author;
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> printAll() {
        List<Author> result = authorDao.getAll();
        result.forEach(this::print);
        return result;
    }

    @Override
    public Author update() {
        Author author = getAuthorById();
        if (author != null) {
            author.setName(io.readLn("enter_name"));
            author.setSurname(io.readLn("enter_surname"));
            authorDao.update(author);
            io.out("done");
        }
        return author;
    }

    @Override
    public long count() {
        long count = authorDao.count();
        io.out("count", count);
        return count;
    }

    @Override
    public int delete() {
        Author author = getAuthorById();
        if (author != null) {
            int rowsAffected = authorDao.deleteById(author.getId());
            io.out("done");
            return rowsAffected;
        }
        return 0;
    }

    private Author getAuthorById() {
        return authorDao.getById(io.readLong("enter_id"));
    }

    private void print(Author author) {
        io.write(author.toString());
    }
}
