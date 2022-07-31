package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    InMemoryUserStorage userStorage;
    UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    private void checkValidationError(HttpServletRequest request, BindingResult res) {
        if (res.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), res.getFieldError());
            throw new ValidationException(Objects.requireNonNull(res.getFieldError()).toString());
        }
    }

    @GetMapping()
    public List<User> findAllUsers(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
        return userStorage.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userStorage.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user
            , BindingResult bindingResult, HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);
        long id = userStorage.createUser(user);
        return ResponseEntity.ok(userStorage.getUserById(id));

    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user
            , BindingResult bindingResult
            , HttpServletRequest request) throws ValidationException {

        checkValidationError(request, bindingResult);

        log.info("Получен запрос к эндпоинту: '{} {}' c телом '{}'",
                request.getMethod(), request.getRequestURI(), user);
        userStorage.updateUser(user);
        return ResponseEntity.ok(user);

    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String addToFriends(@PathVariable long id, @PathVariable long friendId) {
        userService.addToFriends(id, friendId);
        return "Лайк поставлен";
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteFromFriends(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFromFriends(id, friendId);
        return "Лайк удален";
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> getAllCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getAllCommonFriends(id, otherId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> getAllFriends(@PathVariable long id) {
        return userService.getAllFriends(id);
    }

}
