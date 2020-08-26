package topjava.grad.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import topjava.grad.domain.Role;
import topjava.grad.domain.User;
import topjava.grad.service.UserService;
import topjava.grad.util.exception.ErrorType;
import topjava.grad.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.grad.TestUtil.readFromJson;
import static topjava.grad.TestUtil.userHttpBasic;
import static topjava.grad.data.UserTestData.*;

class UserControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserController.REST_URL + "/";

    @Autowired
    private UserService userService;

    @Test
    void create() throws Exception {
        User newUser = getNewUser();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, "newPassword"))
                .with(userHttpBasic(ADMIN)));

        User user = readFromJson(action, User.class);
        Integer id = user.getId();
        newUser.setId(id);
        USER_MATCHER.assertMatch(user, newUser);
        USER_MATCHER.assertMatch(userService.get(id), newUser);
    }

    @Test
    void createInvalid() throws Exception {
        User user = new User(null, "notemail", "djda43", Role.USER);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(user, "djda43"))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        User updatedUser = getUpdatedUser();

        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedUser, "updatedPassword"))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        USER_MATCHER.assertMatch(userService.get(USER_ID), updatedUser);
    }

    @Test
    void updateInvalid() throws Exception {
        User user = new User(USER_ID, "some@email.com", "d", Role.USER);

        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(user, "d"))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        assertThrows(NotFoundException.class, () -> userService.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void list() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.jsonListMatcher(List.of(USER, ADMIN)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.jsonMatcher(USER));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getProfile() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "profile")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.jsonMatcher(USER));
    }
}