package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserValidationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldReturnErrorWhenEmptyEmail() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("");
        user.setLogin("login");
        user.setBirthday(LocalDate.parse("2020-08-20"));
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/users", user,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));

    }
    @Test
    public void shouldReturnErrorWhenNotValidEmail() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("v.v");
        user.setLogin("login");
        user.setBirthday(LocalDate.parse("2020-08-20"));
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/users", user,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));
    }

    @Test
    public void shouldReturnErrorWhenEmptyLogin() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("v@v.com");
        user.setLogin("");
        user.setBirthday(LocalDate.parse("2020-08-20"));
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/users", user,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));
    }

    @Test
    public void shouldReturnErrorWhenDateBirthdayInFuture() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("v@v.com");
        user.setLogin("login");
        user.setBirthday(LocalDate.parse("2023-08-20"));
        String response = this.restTemplate.postForObject("http://localhost:" + port + "/users", user,
                String.class);
        Assertions.assertTrue(response.contains("\"error\":\"Bad Request\""));
        Assertions.assertTrue(response.contains("\"status\":400"));
    }

}
