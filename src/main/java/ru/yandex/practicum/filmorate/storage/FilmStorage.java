package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void create(Film film);
    void update(Film film);
    void delete(long id);
    List<Film> getAll();
    Film getById(long id);

}
