package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;

import java.sql.PreparedStatement;

@Repository
public class LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public LikeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLikeToFilm(int filmId, int userId) {
        String sql = "INSERT INTO LIKED_FILM (USER_ID,FILM_ID)" + "VALUES ((SELECT id FROM USER WHERE id = ?), " + "(SELECT id FROM FILM WHERE id = ?))";
        int result = jdbcTemplate.update(sql, userId, filmId);
        if (result != 1) {
            throw new ObjectNotFoundException();
        }
    }

    public void deleteLikeFromFilm(int filmId, int userId) {
        String sql = "DELETE FROM LIKED_FILM WHERE FILM_ID = ? AND USER_ID = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, filmId);
            stmt.setInt(2, userId);
            return stmt;
        }, keyHolder);
        if (result != 1) {
            throw new ObjectNotFoundException();
        }
    }

    public void deleteLikeFromFilm(int filmId) {
        String sql = "DELETE FROM LIKED_FILM WHERE FILM_ID = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, filmId);
            return stmt;
        }, keyHolder);
    }

}
