package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Long, Film> films = new HashMap<>();
    private long id = 1;

    @Override
    public Film getFilmById(long id) {
        if (!films.containsKey(id)) {
            throw new ObjectNotFoundException();
        }
        return films.get(id);
    }

    @Override
    public void createFilm(Film film) {
        film.setId(getId());
        incrementId();
        films.put(film.getId(), film);
    }

    @Override
    public void updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ObjectNotFoundException();
        }
        films.put(film.getId(), film);

    }

    @Override
    public void deleteFilm() {

    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    private long getId() {
        return id;
    }

    private void incrementId() {
        id++;
    }
}
