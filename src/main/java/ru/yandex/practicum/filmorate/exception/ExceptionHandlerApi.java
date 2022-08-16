package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.GenreController;
import ru.yandex.practicum.filmorate.controller.MpaController;
import ru.yandex.practicum.filmorate.controller.UserController;

import java.util.Map;


@RestControllerAdvice(assignableTypes = {FilmController.class,
        UserController.class,
        MpaController.class,
        GenreController.class})
public class ExceptionHandlerApi {

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationError(final ValidationException e) {
        return Map.of("error", "ошибка валидации, проверьте передаваемые значения");
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, String> handleObjectNotFoundException(final ObjectNotFoundException e) {
        return Map.of("error", "объект не найден");
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleIncorrectCount(final RuntimeException e) {
        return Map.of(
                "error", "что то пошло не так...",
                "errorMessage", e.getMessage()
        );
    }

}
