package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static ru.yandex.practicum.filmorate.validator.RequestValadation.checkValidationError;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmStorage filmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(@Qualifier("filmDBStorage") FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }


    @GetMapping
    public List<Film> findAll(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return filmStorage.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(HttpServletRequest request, @PathVariable int id) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return ResponseEntity.ok(filmStorage.getById(id));
    }

    @DeleteMapping("/{id}")
    public String delete(HttpServletRequest request, @PathVariable int id) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        filmStorage.delete(id);
        filmService.deleteFilmFromRating(id);
        filmService.deleteLikeFromFilm(id);
        filmService.updateFilmRating(id);
        return "Фильм удален";
    }

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film
            , BindingResult bindingResult, HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        filmStorage.create(film);
        filmService.addFilmToRating(film);
        filmService.addGenreFromFilm(film);
        return ResponseEntity.ok(film);

    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film
            , BindingResult bindingResult
            , HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        filmStorage.update(film);
        filmService.updateFilmRating(film.getId());
        filmService.deleteGenresFromFilm(film.getId());
        filmService.addGenreFromFilm(film);
        return ResponseEntity.ok(film);

    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String addLikes(@PathVariable int id, @PathVariable int userId) {
        filmService.addLikeToFilm(id, userId);
        filmService.updateFilmRating(id);
        return "Лайк поставлен";
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLikeFromFilm(id, userId);
        filmService.updateFilmRating(id);
        return "Лайк удален";
    }

    @GetMapping("/popular")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Film> getPopularByCount(@RequestParam(required = false, defaultValue = "10") String count) {
        return filmService.getMostPopularFilms(count);
    }

}
