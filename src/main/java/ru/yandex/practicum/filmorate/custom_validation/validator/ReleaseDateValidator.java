package ru.yandex.practicum.filmorate.custom_validation.validator;

import ru.yandex.practicum.filmorate.custom_validation.annotation.ReleaseDateValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateValidation, String> {
    private String dateStart;

    public void initialize(final ReleaseDateValidation annotationContext) {
        dateStart = annotationContext.dateStart();
    }

    public boolean isValid(String date, ConstraintValidatorContext cxt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate toLocalDate;
        LocalDate startEpochDate = LocalDate.parse(dateStart, formatter);

        try {
            toLocalDate = LocalDate.parse(date, formatter);
        } catch (DateTimeException ex) {
            return false;
        }
        return startEpochDate.isBefore(toLocalDate);

    }
}
