package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dao.GenresDao;
import ru.yandex.practicum.filmorate.model.Genre;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    GenresDao genresDao;

    @Autowired
    public GenreController(GenresDao genresDao) {
        this.genresDao = genresDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getById(HttpServletRequest request, @PathVariable int id) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return ResponseEntity.ok(genresDao.getById(id));
    }

    @GetMapping
    public List<Genre> findAll(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return genresDao.getAll();
    }
}
