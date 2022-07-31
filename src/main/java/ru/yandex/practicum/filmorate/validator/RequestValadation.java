package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
public class RequestValadation {
    public static void checkValidationError(HttpServletRequest request, BindingResult res) {
        if (res.hasErrors()) {
            log.warn("Ошибка валидации метода: '{}' ошибка: '{}' ",
                    request.getRequestURI(), res.getFieldError());
            throw new ValidationException(Objects.requireNonNull(res.getFieldError()).toString());
        }
    }
}
