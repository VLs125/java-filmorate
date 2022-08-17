package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenresDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.RateDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;


@Service
public class FilmService {
    private final GenresDao genresDao;
    private final RateDao rateDao;
    private final LikeDao likeDao;

    @Autowired
    public FilmService(
            GenresDao genresDao, RateDao rateDao, LikeDao likeDao) {
        this.genresDao = genresDao;
        this.rateDao = rateDao;
        this.likeDao = likeDao;

    }

    public void deleteGenresFromFilm(int id) {
        genresDao.deleteGenresFromFilm(id);
    }

    public void addGenreFromFilm(Film film) {
        genresDao.addGenreFromFilm(film);
    }

    public void addFilmToRating(Film film) {
        rateDao.addFilmToRating(film);
    }

    public void addLikeToFilm(int filmId, int userId) {
        likeDao.addLikeToFilm(filmId, userId);
    }

    public void updateFilmRating(int filmId) {
        rateDao.updateFilmRating(filmId);
    }

    public void deleteLikeFromFilm(int filmId, int userId) {
        likeDao.deleteLikeFromFilm(filmId, userId);
    }

    public void deleteLikeFromFilm(int filmId) {
        likeDao.deleteLikeFromFilm(filmId);
    }

    public List<Film> getMostPopularFilms(String count) {
        return rateDao.getMostPopularFilms(count);
    }


    public void deleteFilmFromRating(int id) {
        rateDao.deleteFromRating(id);
    }

}