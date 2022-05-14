package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Long,Film> films = new HashMap<>();

    private void checkValidationError(HttpServletRequest request, BindingResult res) {
        if (res.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), res.getFieldError());
            throw new ValidationException(Objects.requireNonNull(res.getFieldError()).toString());
        }
    }

    @GetMapping
    public List<Film> findAllFilms(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film
            , BindingResult bindingResult, HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        films.put(film.getId(),film);
        return ResponseEntity.ok(film);

    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film
            , BindingResult bindingResult
            , HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        films.put(film.getId(),film);
        return ResponseEntity.ok(film);

    }
}
