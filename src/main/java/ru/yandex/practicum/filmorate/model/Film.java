package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.custom_validation.annotation.ReleaseDateValidation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Slf4j
public class Film {
    private int id;
    @NotEmpty
    @NotNull
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    @ReleaseDateValidation(message = "Дата не должна быть раньше чем 1885-12-28",dateStart = "1895-12-28")
    private String releaseDate;
    @Min(1)
    private int duration;
}
