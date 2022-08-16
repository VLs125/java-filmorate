package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.custom_annotation.ReleaseDateValidation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Film {
    private int id;
    @NotEmpty
    @NotNull
    private String name;
    @Size(min = 1, max = 200)
    @NotNull
    private String description;
    @ReleaseDateValidation(message = "Дата не должна быть раньше чем 1885-12-28", dateStart = "1895-12-28")
    private LocalDate releaseDate;
    @Min(1)
    @NotNull
    private int duration;
    @NotNull
    @JsonProperty(value = "mpa")
    private Mpa mpa_id;
    @JsonProperty(value = "genres")
    private Set<Genre> genres_id;

    public Film(int id, String name, String description, LocalDate release_date, int duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = release_date;
        this.duration = duration;
        this.mpa_id = mpa;
    }
}
