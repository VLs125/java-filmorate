package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @Override
    public Film getById(int id) {
        if (!films.containsKey(id)) {
            throw new ObjectNotFoundException();
        }
        return films.get(id);
    }

    @Override
    public void create(Film film) {
        film.setId(getId());
        incrementId();
        films.put(film.getId(), film);
    }

    @Override
    public void update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ObjectNotFoundException();
        }
        films.put(film.getId(), film);

    }

    @Override
    public void delete(int id) {
        if (!films.containsKey(id)) {
            throw new ObjectNotFoundException();
        }
        films.remove(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    private int getId() {
        return id;
    }

    private void incrementId() {
        id++;
    }
}
