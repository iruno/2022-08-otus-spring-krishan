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
import ru.otus.spring.domain.Genre;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public long count() {
        Long count = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(
                "select count(*) from genre", Long.class);

        return count == null ? 0 : count;
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "insert into genre (name) values (:name)", parameterSource, keyHolder, new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public int update(Genre genre) {
        return namedParameterJdbcTemplate.update(
                "update genre set name=:name where id=:id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        return namedParameterJdbcTemplate.queryForObject(
                "select id, name from genre where id=:id",
                Map.of("id", id), new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(
                "select id, name from genre", new GenreMapper());
    }

    @Override
    public int deleteById(long id) {
        return namedParameterJdbcTemplate.update(
                "delete from genre where id=:id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return Genre.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }
}
