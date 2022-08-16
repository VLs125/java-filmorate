package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genre {
    private final int id;
    private String name;
}
