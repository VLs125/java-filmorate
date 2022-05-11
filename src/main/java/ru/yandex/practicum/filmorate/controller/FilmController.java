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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/")
public class FilmController {
    private final HashSet<Film> films = new HashSet<>();

    @GetMapping("/films")
    public List<Film> findAllFilms(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return new ArrayList<>(films);
    }

    @PostMapping(value = "/films")
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film
            , BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        if (bindingResult.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), bindingResult.getFieldError());
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).toString());
        }
        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        films.add(film);
        return new ResponseEntity<>(film, HttpStatus.OK);

    }

    @PutMapping("/films")
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film
            , BindingResult bindingResult
            , HttpServletRequest request) throws ValidationException {

        if (bindingResult.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), bindingResult.getFieldError());
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).toString());
        }
        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), film);
        films.add(film);
        return new ResponseEntity<>(film, HttpStatus.OK);

    }
}
