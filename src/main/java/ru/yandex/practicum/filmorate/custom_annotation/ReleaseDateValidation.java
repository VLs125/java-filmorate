package ru.yandex.practicum.filmorate.custom_annotation;

import ru.yandex.practicum.filmorate.validator.ReleaseDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
public @interface ReleaseDateValidation {
    String dateStart();
     String message() default "{ru.yandex.practicum.filmorate.custom_validation.annotation.ReleaseDateValidation.message}";

     Class<?>[] groups() default {};

     Class<? extends Payload>[] payload() default {};

}
