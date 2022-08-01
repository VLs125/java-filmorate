package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.comparator.FilmsLikeComparator;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FilmService {
    private FilmsLikeComparator comparator;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmsLikeComparator comparator,
                       FilmStorage filmStorage) {
        this.comparator = comparator;
        this.filmStorage = filmStorage;
    }

    HashMap<Long, List<Long>> filmRateStorage = new HashMap<>();
    TreeMap<Integer, Long> sortedMap = new TreeMap<>(comparator);

    public void addFilmToRatingWithoutLikes(Film film) {
        filmRateStorage.put(film.getId(), new ArrayList<>());
        sortedMap.put(filmRateStorage.get(film.getId()).size(), film.getId());
    }

    public void addLikeToFilm(Long filmId, Long userId) {
        List<Long> userLikedFilmList = filmRateStorage.get(filmId);
        if (!userLikedFilmList.contains(userId)) {
            userLikedFilmList.add(userId);
        }
        filmRateStorage.put(filmId, userLikedFilmList);
        sortedMap.put(filmRateStorage.get(filmId).size(), filmId);

    }

    public void deleteLikeFromFilm(Long filmId, Long userId) {
        List<Long> userLikedFilmList = filmRateStorage.get(filmId);
        if (!userLikedFilmList.contains(userId)) {
            throw new ObjectNotFoundException();
        }
        userLikedFilmList.remove(userId);
        filmRateStorage.put(filmId, userLikedFilmList);
        sortedMap.put(filmRateStorage.get(filmId).size(), filmId);

    }

    public List<Film> getTenMostPopularFilms(String count) {
        int countFromString = Integer.parseInt(count);
        List<Film> result = new ArrayList<>();
        List<Long> filmIdList = sortedMap.values().stream().limit(countFromString).collect(Collectors.toList());
        filmIdList.forEach(i -> result.add(filmStorage.getById(i)));
        return result;

    }

    public void deleteFilmFromRating(long id) {
        sortedMap.remove(filmRateStorage.get(id).size());
        filmRateStorage.remove(id);

    }

}