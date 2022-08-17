package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {

    private int id = 1;
    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        checkName(user);
        user.setId(getId());
        users.put(user.getId(), user);
        incrementId();
        return user;
    }

    @Override
    public void delete(int userId) {
        if (!users.containsKey(userId)) {
            throw new ObjectNotFoundException();
        }
        users.remove(userId);
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ObjectNotFoundException();
        }
        checkName(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException();
        }
        return users.get(id);
    }

    private int getId() {
        return id;
    }

    private void incrementId() {
        id++;
    }

    private void checkName(User user) {
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
