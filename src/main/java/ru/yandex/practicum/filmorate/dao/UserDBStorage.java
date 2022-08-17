package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDBStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        String sql = "insert into USER (EMAIL, LOGIN, NAME, BIRTHDAY) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setString(1, user.getEmail());
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthday));
            }
            return stmt;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM USER WHERE ID = ?";
        Object[] args = new Object[]{id};
        int result = jdbcTemplate.update(sql, args);
        if (result != 1) {
            throw new ObjectNotFoundException(String.format("Пользователь с id = %s , не найден", id));
        }
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE USER SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE ID = ?";
        int result = jdbcTemplate.update(sql, user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        if (result != 1) {
            throw new ObjectNotFoundException();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        final String sqlQuery = "SELECT * FROM USER";
        return jdbcTemplate.query(sqlQuery, UserDBStorage::makeUser);
    }

    @Override
    public User getById(int id) {
        final String sql = "SELECT * FROM USER WHERE ID = ?";
        final List<User> users = jdbcTemplate.query(sql, UserDBStorage::makeUser, id);
        if (users.size() != 1) {
            throw new ObjectNotFoundException(String.format("Пользователь с id = %s , не найден", id));
        }
        return users.get(0);

    }

    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate());
    }
}
