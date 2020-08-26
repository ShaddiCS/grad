package topjava.grad.data;

import org.springframework.security.core.userdetails.UserDetails;
import topjava.grad.TestMatcher;
import topjava.grad.TestUtil;
import topjava.grad.domain.Role;
import topjava.grad.domain.User;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = new TestMatcher<>(User.class, "password", "votes");
    public static final TestMatcher<UserDetails> USER_DETAILS_MATCHER = new TestMatcher<>(UserDetails.class, "password", "votes");

    public static final Integer USER_ID = 100000;
    public static final Integer ADMIN_ID = 100001;

    public static final User USER = new User(USER_ID, "user@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "admin@gmail.com", "admin", Role.USER, Role.ADMIN);

    public static User getNewUser() {
        return new User(null, "new@mail.com", "newPassword", Role.USER);
    }

    public static User getUpdatedUser() {
        return new User(USER_ID, "updated@yandex.ru", "updatedPassword", Role.ADMIN);
    }

    public static String jsonWithPassword(User user, String password) {
        return TestUtil.writeAdditionalProps(user, "password", password);
    }
}
