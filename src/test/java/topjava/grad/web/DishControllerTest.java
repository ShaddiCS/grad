package topjava.grad.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import topjava.grad.domain.Dish;
import topjava.grad.domain.to.DishTo;
import topjava.grad.service.DishService;
import topjava.grad.util.exception.ErrorType;
import topjava.grad.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.grad.TestUtil.*;
import static topjava.grad.data.DishTestData.*;
import static topjava.grad.data.MenuTestData.MENU_1;
import static topjava.grad.data.MenuTestData.MENU_1_ID;
import static topjava.grad.data.UserTestData.ADMIN;
import static topjava.grad.data.UserTestData.USER;

class DishControllerTest extends AbstractControllerTest {

    private final static String REST_URL = DishController.REST_URL + "/";

    @Autowired
    private DishService dishService;

    @Test
    void create() throws Exception {
        DishTo dishTo = getNewDish();
        Dish newDish = new Dish(dishTo);

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(dishTo))
                .with(userHttpBasic(ADMIN)));

        Dish dish = readFromJson(action, Dish.class);
        Integer id = dish.getId();
        newDish.setId(id);
        DISH_MATCHER.assertMatch(dish, newDish);
        DISH_MATCHER.assertMatch(dishService.get(id), newDish);
    }

    @Test
    void createInvalid() throws Exception {
        Dish newDish = new Dish(null, "new Dish", 5, MENU_1);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(newDish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print());

        assertThrows(NotFoundException.class, () -> dishService.get(DISH_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        DishTo dishTo = getUpdatedDish();
        Dish newDish = new Dish(dishTo);
        Integer id = dishTo.getId();
        perform(MockMvcRequestBuilders.put(REST_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(dishTo))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishService.get(id), newDish);
    }

    @Test
    void updateInvalid() throws Exception {
        DishTo updatedDish = new DishTo(DISH_1_ID, "New Dish", 4, MENU_1_ID);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(updatedDish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.jsonMatcher(DISH_1));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 1)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_1_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}