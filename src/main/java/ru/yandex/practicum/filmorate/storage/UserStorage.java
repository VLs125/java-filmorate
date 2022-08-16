package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    void delete(int id);

    User update(User user);

    List<User> getAll();

    User getById(int id);
}
