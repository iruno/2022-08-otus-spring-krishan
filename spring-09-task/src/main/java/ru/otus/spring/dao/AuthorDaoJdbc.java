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

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public long count() {
        Long count = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(
                "select count(*) from author", Long.class);

        return count == null ? 0 : count;
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", author.getName());
        parameterSource.addValue("surname", author.getSurname());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "insert into author (name, surname) values (:name, :surname)", parameterSource, keyHolder,
                new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public int update(Author author) {
        return namedParameterJdbcTemplate.update(
                "update author set name=:name, surname=:surname where id=:id",
                Map.of("id", author.getId(), "name", author.getName(), "surname", author.getSurname()));
    }

    @Override
    public Author getById(long id) {
        return namedParameterJdbcTemplate.queryForObject(
                "select id, name, surname from author where id=:id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(
                "select id, name, surname from author", new AuthorMapper());
    }

    @Override
    public int deleteById(long id) {
        return namedParameterJdbcTemplate.update(
                "delete from author where id=:id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {

            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .build();
        }
    }
}
