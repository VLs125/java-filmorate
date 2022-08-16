package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;


@Service
public class UserService {
    UserStorage userStorage;
    FriendshipDao friendshipDao;

    @Autowired
    public UserService(@Qualifier("userDBStorage") UserStorage userStorage,
                       FriendshipDao friendshipDao) {
        this.userStorage = userStorage;
        this.friendshipDao = friendshipDao;
    }


    public void addToFriends(int id, int userId) {
        friendshipDao.addToFriends(id, userId);
    }

    public void deleteFromFriends(int id, int userId) {
        friendshipDao.deleteFromFriends(id, userId);
    }

    public List<User> getAllFriends(int id) {
        return friendshipDao.getAllFriends(id);
    }

    public List<User> getAllCommonFriends(int id, int userId) {
        return friendshipDao.getAllCommonFriends(id, userId);
    }

    public void deleteUserFromFriendsWhenUserDeleted(int id) {
        friendshipDao.deleteFromFriendsWhenUserDeleted(id);
    }

}
