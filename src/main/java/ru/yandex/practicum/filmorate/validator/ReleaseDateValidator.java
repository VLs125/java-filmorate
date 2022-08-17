package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.custom_annotation.ReleaseDateValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateValidation, LocalDate> {
    private String dateStart;

    public void initialize(final ReleaseDateValidation annotationContext) {
        dateStart = annotationContext.dateStart();
    }

    public boolean isValid(LocalDate date, ConstraintValidatorContext cxt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate toLocalDate;
        LocalDate startEpochDate = LocalDate.parse(dateStart, formatter);

        try {
            toLocalDate = date;
        } catch (DateTimeException ex) {
            return false;
        }
        return startEpochDate.isBefore(toLocalDate);

    }
}
