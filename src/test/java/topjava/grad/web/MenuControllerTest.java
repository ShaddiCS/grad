package topjava.grad.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import topjava.grad.domain.Menu;
import topjava.grad.domain.to.MenuTo;
import topjava.grad.service.MenuService;
import topjava.grad.util.exception.ErrorType;
import topjava.grad.util.exception.NotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.grad.TestUtil.*;
import static topjava.grad.data.MenuTestData.*;
import static topjava.grad.data.RestaurantTestData.PLACE_1;
import static topjava.grad.data.RestaurantTestData.PLACE_1_ID;
import static topjava.grad.data.UserTestData.ADMIN;
import static topjava.grad.data.UserTestData.USER;

class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuController.REST_URL + "/";

    @Autowired
    private MenuService menuService;

    @Test
    void list() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.jsonMatcher(MENU_1, MENU_2));
    }

    @Test
    void getAllByPlace() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-place?restaurant=" + PLACE_1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.jsonMatcher(MENU_1));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-date?date=" + LocalDate.now().toString())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.jsonMatcher(MENU_1, MENU_2));
    }

    @Test
    void create() throws Exception {
        MenuTo menuTo = new MenuTo(null, PLACE_1_ID, LocalDate.now());
        Menu newMenu = new Menu(menuTo);
        newMenu.setRestaurant(PLACE_1);

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(menuTo))
                .with(userHttpBasic(ADMIN)));

        Menu menu = readFromJson(action, Menu.class);
        Integer id = menu.getId();
        newMenu.setId(id);
        MENU_MATCHER.assertMatch(menu, newMenu);
        MENU_MATCHER.assertMatch(menuService.get(id), newMenu);
    }

    @Test
    void createInvalid() throws Exception {
        MenuTo menuTo = new MenuTo(null, PLACE_1_ID, null);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(menuTo))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        MenuTo menuTo = getUpdated();
        Integer id = menuTo.getId();
        Menu newMenu = new Menu(menuTo);
        newMenu.setRestaurant(PLACE_1);

        perform(MockMvcRequestBuilders.put(REST_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(menuTo))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());

        MENU_MATCHER.assertMatch(menuService.get(id), newMenu);
    }

    @Test
    void updateInvalid() throws Exception {
        MenuTo menuTo = new MenuTo(MENU_1_ID, PLACE_1_ID, null);

        perform(MockMvcRequestBuilders.put(REST_URL + MENU_1_ID)
                .content(writeValues(menuTo))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());
        assertThrows(NotFoundException.class, () -> menuService.get(MENU_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.jsonMatcher(MENU_1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_1_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 1)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}