package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.List;

public class ObjectNotFoundValidation {
    public static void checkObjectNotNull(User user, Film film, List<User> userLikedFIlmList) {
        if (user == null || film == null || userLikedFIlmList == null) {
            throw new ObjectNotFoundException();
        }
    }
    public static void checkUserHaveFriends(HashSet<User> userSet, User user) {
        if (userSet == null || user == null) {
            throw new ObjectNotFoundException();
        }
    }
}
