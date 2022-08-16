package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
//    private final UserDBStorage userStorage;
//    private final UserService userService;
////
//    @Test
//    public void testFindUserById() {
//
//        Optional<User> userOptional = Optional.ofNullable(userStorage.getById(1));
//
//        assertThat(userOptional)
//                .isPresent()
//                .hasValueSatisfying(user ->
//                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
//                );
//    }
//
//    @Test
//    public void testGetAllUsers() {
//
//        Optional<List<User>> userOptional = Optional.ofNullable(userStorage.getAll());
//
//        assertThat(userOptional)
//                .isPresent()
//                .hasValueSatisfying(user -> {
//                            assertThat(user.size() == 4).isTrue();
//                        }
//                );
//    }
//
//    @Test
//    public void testDeleteUserById() {
//
//        userStorage.delete(1);
//        userService.deleteUserFromFriendsStorage(1);
//        Optional<List<User>> userOptional = Optional.ofNullable(userStorage.getAll());
//
//        assertThat(userOptional)
//                .isPresent()
//                .hasValueSatisfying(user -> {
//                            assertThat(user.size() == 3).isTrue();
//                        }
//                );
//    }
}
