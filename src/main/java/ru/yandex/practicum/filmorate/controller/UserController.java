package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final HashMap<Long,User> users = new HashMap<>();

    private void checkValidationError(HttpServletRequest request, BindingResult res) {
        if (res.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), res.getFieldError());
            throw new ValidationException(Objects.requireNonNull(res.getFieldError()).toString());
        }
    }

    @GetMapping
    public List<User> findAllUsers(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user
            , BindingResult bindingResult, HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        users.put(user.getId(),user);
        return ResponseEntity.ok(user);

    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user
            , BindingResult bindingResult
            , HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), user);
        users.put(user.getId(),user);
        return  ResponseEntity.ok(user);

    }

}
