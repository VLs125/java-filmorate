package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }


    @GetMapping
    public List<Film> findAllFilms(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return filmStorage.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(HttpServletRequest request, @PathVariable int id) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return ResponseEntity.ok(filmStorage.getById(id));
    }

    @DeleteMapping("/{id}")
    public String deleteFilm(HttpServletRequest request, @PathVariable int id) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        filmStorage.delete(id);
        filmService.deleteFilmFromRating(id);
        return "Фильм удален";
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film
            , BindingResult bindingResult, HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        filmStorage.create(film);
        filmService.addFilmToRatingWithoutLikes(film);
        return ResponseEntity.ok(film);

    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film
            , BindingResult bindingResult
            , HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        filmStorage.update(film);
        return ResponseEntity.ok(film);

    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String addLikesToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.addLikeToFilm(id, userId);
        return "Лайк поставлен";
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteLikesFromFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLikeFromFilm(id, userId);
        return "Лайк удален";
    }

    @GetMapping("/popular")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Film> getPopularFilmsByCount(@RequestParam(required = false, defaultValue = "10") String count) {
        return filmService.getTenMostPopularFilms(count);
    }

}
