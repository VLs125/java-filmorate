package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MpaDao {
    private final JdbcTemplate jdbcTemplate;

    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Mpa> getAll() {
        final String sqlQuery = "SELECT * FROM MPA";
        return jdbcTemplate.query(sqlQuery, MpaDao::makeMpa);
    }

    public Mpa getById(int id) {
        final String sql = "SELECT * FROM MPA WHERE ID = ?";
        final List<Mpa> mpa = jdbcTemplate.query(sql, MpaDao::makeMpa, id);
        if (mpa.size() != 1) {
            throw new ObjectNotFoundException(String.format("MPA с id = %s , не найден", id));
        }
        return mpa.get(0);

    }

    public static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("id"),
                rs.getString("title"));
    }

}
