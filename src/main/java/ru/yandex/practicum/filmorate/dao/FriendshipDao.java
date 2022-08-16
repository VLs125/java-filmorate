package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendshipDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToFriends(int id, int userId) {

        String sql = "INSERT INTO FRIENDSHIP (USER_SENDER_INVITE, USER_RECEIVED_INVITE, IS_ACCEPTED) " +
                "VALUES((SELECT id FROM USER WHERE id = ?), " +
                "(SELECT id FROM USER WHERE id = ?), ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int result = jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
                stmt.setInt(1, id);
                stmt.setInt(2, userId);
                stmt.setBoolean(3, false);
                return stmt;
            }, keyHolder);
            if (result != 1) {
                throw new ObjectNotFoundException();
            }
        } catch (RuntimeException ex) {
            throw new ObjectNotFoundException();
        }

    }

    public void deleteFromFriends(int id, int userId) {
        String sql = "DELETE FROM FRIENDSHIP WHERE USER_SENDER_INVITE = ? AND USER_RECEIVED_INVITE = ?";
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt;
        });
        if (result != 1) {
            throw new ObjectNotFoundException();
        }
    }

    public void deleteFromFriendsWhenUserDeleted(int id) {
        String sql = "DELETE FROM FRIENDSHIP WHERE USER_SENDER_INVITE = ? OR USER_RECEIVED_INVITE = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            return stmt;
        });
    }

    public List<User> getAllFriends(int id) {

        final String sql = "SELECT * " +
                "FROM USER " +
                "WHERE ID IN(SELECT USER_RECEIVED_INVITE FROM FRIENDSHIP WHERE USER_SENDER_INVITE = ?) ";
        try {
            final List<User> users = jdbcTemplate.query(sql, UserDBStorage::makeUser, id);
            return users;
        } catch (Exception ex) {
            throw new ObjectNotFoundException();
        }
    }

    public List<User> getAllCommonFriends(int id, int userId) {
        final String sql = "SELECT * " +
                "FROM USER " +
                "WHERE ID IN(SELECT USER_RECEIVED_INVITE FROM FRIENDSHIP WHERE USER_SENDER_INVITE = ? " +
                "AND USER_RECEIVED_INVITE != ?) " +
                "UNION " +
                "SELECT * FROM USER " +
                "WHERE ID IN(SELECT USER_RECEIVED_INVITE FROM FRIENDSHIP WHERE USER_SENDER_INVITE = ?" +
                "AND USER_RECEIVED_INVITE != ?)";

        final List<User> users = jdbcTemplate.query(sql, UserDBStorage::makeUser, id, userId, userId, id);
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        return users;
    }


}
