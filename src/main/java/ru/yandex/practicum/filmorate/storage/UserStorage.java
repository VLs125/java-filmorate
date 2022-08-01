package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    long create(User user);
    void delete(long id);
    void update(User user);
    List<User> getAll();
    User getById(long id);
}
