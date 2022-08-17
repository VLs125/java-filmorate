package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RateDao {
    private final JdbcTemplate jdbcTemplate;

    public RateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFilmToRating(Film film) {
        String sql = "INSERT INTO FILM_RATE (FILM_ID, RATE) " +
                "SELECT f.id, " +
                "COUNT(lf.user_id) " +
                "FROM FILM f  " +
                "LEFT JOIN LIKED_FILM lf ON f.id = lf.film_id " +
                "WHERE f.id = ? " +
                "GROUP BY lf.film_id";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setInt(1, film.getId());
            return stmt;
        }, keyHolder);
        if (result != 1) {
            throw new ObjectNotFoundException();
        }
    }

    public void updateFilmRating(int filmId) {
        String sql = "UPDATE FILM_RATE " +
                "SET RATE = (SELECT COUNT(lf.user_id) FROM LIKED_FILM lf WHERE FILM_ID = ? GROUP BY lf.film_id) " +
                "WHERE FILM_ID = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setInt(1, filmId);
            stmt.setInt(2, filmId);
            return stmt;
        }, keyHolder);
        if (result != 1) {
            throw new ObjectNotFoundException();
        }
    }

    public List<Film> getMostPopularFilms(String count) {
        int countFromString = Integer.parseInt(count);
        final String sqlQuery = "SELECT f.*," +
                "M.TITLE mpa_name, " +
                "ARRAY_AGG(FG.genre_id || ':' || g.name) AS genres_id " +
                "FROM FILM as F " +
                "LEFT JOIN FILM_RATE FR on F.id = FR.film_id " +
                "LEFT JOIN FILM_GENRES FG ON F.id = FG.film_id  " +
                "LEFT JOIN GENRE G ON FG.genre_id = G.id " +
                "LEFT JOIN MPA M ON F.mpa_id  = M.id " +
                "GROUP BY f.ID " +
                "ORDER BY FR.RATE DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, FilmDBStorage::makeFilm, countFromString);
    }

    public void deleteFromRating(int id) {
        String sql = "DELETE FROM FILM_RATE WHERE FILM_ID = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setInt(1, id);
            return stmt;
        }, keyHolder);
        if (result != 1) {
            throw new ObjectNotFoundException(String.format("Фильм с id = %s , не найден", id));
        }
    }

}
