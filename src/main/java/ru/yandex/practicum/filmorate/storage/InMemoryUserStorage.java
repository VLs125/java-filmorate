package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {

    private long id = 1;
    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public long createUser(User user) {
        checkName(user);
        user.setId(getId());
        users.put(user.getId(), user);
        incrementId();
        return user.getId();
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getId());
    }

    @Override
    public void updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ObjectNotFoundException();
        }
        checkName(user);
        users.put(user.getId(), user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException();
        }
        return users.get(id);
    }

    private long getId() {
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
