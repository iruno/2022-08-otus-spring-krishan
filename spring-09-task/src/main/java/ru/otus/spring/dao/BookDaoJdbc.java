package ru.otus.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public long count() {
        Long count = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(
                "select count(*) from book", Long.class);

        return count == null ? 0 : count;
    }

    @Override
    public long insert(Book book) {
        {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("title", book.getTitle());
            parameterSource.addValue("genre_id", book.getGenre().getId());
            parameterSource.addValue("author_id", book.getAuthor().getId());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(
                    "insert into book (title, genre_id, author_id) values (:title, :genre_id, :author_id)",
                    parameterSource, keyHolder, new String[]{"id"});
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
    }

    @Override
    public int updateTitle(Book book) {
        return namedParameterJdbcTemplate.update(
                "update book set title=:title where id=:id",
                Map.of("id", book.getId(), "title", book.getTitle()));

    }

    @Override
    public Book getById(long id) {
        return namedParameterJdbcTemplate.queryForObject(
                "select b.id, b.title, b.author_id, b.genre_id, a.name author_name, a.surname author_surname, g.name genre_name "
                        +
                        "from book b " +
                        "join genre g on g.id = b.genre_id " +
                        "join author a on a.id = b.author_id " +
                        "where b.id = :id ",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(
                "select b.id, b.title, b.author_id, b.genre_id, a.name author_name, a.surname author_surname, g.name genre_name "
                        +
                        "from book b " +
                        "join genre g on g.id = b.genre_id " +
                        "join author a on a.id = b.author_id ",
                new BookMapper());
    }

    @Override
    public int deleteById(long id) {
        return namedParameterJdbcTemplate.update(
                "delete from book where id=:id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            return Book.builder().id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .author(Author.builder()
                            .id(rs.getLong("author_id"))
                            .name(rs.getString("author_name"))
                            .surname(rs.getString("author_surname"))
                            .build())
                    .genre(Genre.builder()
                            .id(rs.getLong("genre_id"))
                            .name(rs.getString("genre_name"))
                            .build())
                    .build();
        }
    }
}
