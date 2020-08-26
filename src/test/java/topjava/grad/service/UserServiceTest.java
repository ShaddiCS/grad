package topjava.grad.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import topjava.grad.domain.Role;
import topjava.grad.domain.User;
import topjava.grad.repo.UserRepo;
import topjava.grad.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static topjava.grad.data.UserTestData.*;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getAll() {
        USER_MATCHER.assertMatch(userService.getAll(), List.of(withEncodedPassword(USER), withEncodedPassword(ADMIN)));
    }

    @Test
    void get() {
        USER_MATCHER.assertMatch(userService.get(USER_ID), withEncodedPassword(USER));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class,() -> userService.get(1));
    }

    @Test
    void getByMail() {
        USER_DETAILS_MATCHER.assertMatch(userService.loadUserByUsername(USER.getEmail()), withEncodedPassword(USER));
    }

    @Test
    void getByMailNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("wrong@email.com"));
    }

    @Test
    void delete() {
        userService.delete(USER_ID);
        assertNull(userRepo.findById(USER_ID).orElse(null));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class,() -> userService.delete(1));
    }

    @Test
    void update() {
        User updatedUser = getUpdatedUser();
        userService.update(updatedUser);
        USER_MATCHER.assertMatch(userService.get(USER_ID), updatedUser);
    }

    @Test
    void create() {
        User newUser = getNewUser();
        User user = userService.create(newUser);
        newUser.setId(user.getId());

        USER_MATCHER.assertMatch(user, newUser);
    }

    @Test
    void createDuplicateMail() {
        assertThrows(DataAccessException.class,
                () -> userService.create(new User(null, "user@yandex.ru", "newPass", Role.USER)));
    }

    User withEncodedPassword(User user) {
        User newUser = new User(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return newUser;
    }
}