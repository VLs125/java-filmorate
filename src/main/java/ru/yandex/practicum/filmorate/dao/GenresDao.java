package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenresDao {
    private final JdbcTemplate jdbcTemplate;

    public GenresDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Genre> getAll() {
        final String sqlQuery = "SELECT * FROM GENRE";
        return jdbcTemplate.query(sqlQuery, GenresDao::makeGenre);
    }

    public Genre getById(int id) {
        final String sql = "SELECT * FROM GENRE WHERE ID = ?";
        final List<Genre> genre = jdbcTemplate.query(sql, GenresDao::makeGenre, id);
        if (genre.size() != 1) {
            throw new ObjectNotFoundException(String.format("Genre с id = %s , не найден", id));
        }
        return genre.get(0);

    }
    public void addGenreFromFilm(Film film) {
        if (film.getGenres_id() == null || film.getGenres_id().isEmpty()) {
            return;
        }
        String sql = "INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) " +
                "VALUES((SELECT id FROM FILM WHERE id = ?), " +
                "(SELECT id FROM GENRE WHERE id = ?)  )";
        film.getGenres_id().forEach(genre -> {
            jdbcTemplate.execute(sql, (PreparedStatementCallback<Boolean>) ps -> {
                ps.setInt(1, film.getId());
                ps.setInt(2, genre.getId());
                return ps.execute();
            });
        });
    }
    public void deleteGenresFromFilm(int id) {
        String sql = "DELETE FROM FILM_GENRES WHERE FILM_ID = ? ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt;
        }, keyHolder);
    }


    public static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("id"),
                rs.getString("name"));
    }
}
