package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.yandex.practicum.filmorate.model.Film;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmValidationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnErrorWhenEmptyFilmName() {
        Film film = new Film();
        film.setName(null);
        film.setDescription("descriptipn");
        film.setId(1);
        film.setReleaseDate("1965-12-22");
        film.setDuration(200);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/films", film,
                String.class);
        System.out.println(response);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));

    }

    @Test
    public void shouldReturnErrorWhenDescriptionOverLimit() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("descriptipn".repeat(200));
        film.setId(1);
        film.setReleaseDate("1965-12-22");
        film.setDuration(200);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/films", film,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));
    }

    @Test
    public void shouldReturnErrorWhenDescriptionUnderLimit() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("");
        film.setId(1);
        film.setReleaseDate("1965-12-22");
        film.setDuration(200);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/films", film,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));
    }

    @Test
    public void shouldReturnErrorWhenReleaseDateBeforeBorderline() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setId(1);
        film.setReleaseDate("1865-12-22");
        film.setDuration(200);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/films", film,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));
    }

    @Test
    public void shouldReturnErrorWhenDurationZero() {
        Film film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setId(1);
        film.setReleaseDate("1865-12-22");
        film.setDuration(0);
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/films", film,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));
    }
}

