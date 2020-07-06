package topjava.grad.data;

import org.springframework.security.core.userdetails.UserDetails;
import topjava.grad.TestMatcher;
import topjava.grad.domain.Role;
import topjava.grad.domain.User;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = new TestMatcher<>("password", "votes");
    public static final TestMatcher<UserDetails> USER_DETAILS_MATCHER = new TestMatcher<>("password", "votes");

    public static final Integer USER_ID = 100000;
    public static final Integer ADMIN_ID = 100001;

    public static final User USER = new User(USER_ID, "user@yandex.ru", "$2a$08$QtgY0EZyruarCcfdU8E/hePeEHjfuyL9iFfwFW1mC.A2tDv9GMq3e", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "admin@gmail.com", "$2a$08$4hMIdF/hu1F45srb7QKSDut5sGyDF7cLIwBOjerEL5xNOCYlHUkCS", Role.USER, Role.ADMIN);

    public static User getNewUser() {
        return new User(null, "new@mail.com", "newPassword", Role.USER);
    }

    public static User getUpdatedUser() {
        return new User(USER_ID, "updated@yandex.ru", "$2a$08$QtgY0EZyruarCcfdU8E/hePeEHjfuyL9iFfwFW1mC.A2tDv9GMq3e", Role.ADMIN);
    }
}
