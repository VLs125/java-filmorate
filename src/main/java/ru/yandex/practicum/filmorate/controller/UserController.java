package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {
    private final HashSet<User> users = new HashSet<>();

    @GetMapping("/users")
    public List<User> findAllUsers(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return new ArrayList<>(users);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user
            , BindingResult bindingResult, HttpServletRequest request) throws ValidationException {

        if (bindingResult.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), bindingResult.getFieldError());
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).toString());
        }
            users.add(user);
            return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user
            , BindingResult bindingResult
            ,HttpServletRequest request) throws ValidationException {

        if (bindingResult.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), bindingResult.getFieldError());
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).toString());
        }
        if(user.getName().isEmpty() || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), user);
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

}
