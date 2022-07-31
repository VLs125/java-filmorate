package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    HashMap<User, HashSet<User>> friendsStorage = new HashMap<>();

    private void addFriends(User user, User otherUser) {
        if (!friendsStorage.containsKey(user)) {
            HashSet<User> userFriends = new HashSet<>();
            userFriends.add(otherUser);
            friendsStorage.put(user, userFriends);
        }
        HashSet<User> userFriends = friendsStorage.get(user);
        userFriends.add(otherUser);
        friendsStorage.put(user, userFriends);
    }

    public void addToFriends(long id, long userId) {
        User otherUser = userStorage.getUserById(userId);
        User user = userStorage.getUserById(id);
        addFriends(user, otherUser);
        addFriends(otherUser, user);

    }

    public void deleteFromFriends(long id, long userId) {
        User otherUser = userStorage.getUserById(userId);
        User user = userStorage.getUserById(id);
        if (!friendsStorage.containsKey(user)) {
            throw new ObjectNotFoundException();
        }
        HashSet<User> friends = friendsStorage.get(user);
        friends.remove(otherUser);
        friendsStorage.put(user, friends);

    }

    public List<User> getAllFriends(long id) {
        User user = userStorage.getUserById(id);
        if (!friendsStorage.containsKey(user)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(friendsStorage.get(user));
    }

    public List<User> getAllCommonFriends(long id, long userId) {
        HashSet<User> mergedSet = new HashSet<>();
        User user = userStorage.getUserById(id);
        User otherUser = userStorage.getUserById(userId);
        if (!friendsStorage.containsKey(user)) {
            return new ArrayList<>();
        }
        Stream.of(friendsStorage.get(user).stream().filter(i -> i.getId() != userId).collect(Collectors.toList())
                , friendsStorage.get(otherUser).stream().filter(i -> i.getId() != id).collect(Collectors.toList()))
                .forEach(mergedSet::addAll);

        return new ArrayList<>(mergedSet);
    }

}
