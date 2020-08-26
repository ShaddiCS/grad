package topjava.grad.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.RestaurantService;
import topjava.grad.util.exception.ErrorType;
import topjava.grad.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.grad.TestUtil.*;
import static topjava.grad.data.RestaurantTestData.*;
import static topjava.grad.data.UserTestData.ADMIN;
import static topjava.grad.data.UserTestData.USER;

class RestaurantControllerTest extends AbstractControllerTest {

    private final static String REST_URL = RestaurantController.REST_URL + "/";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void register() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(newRestaurant))
                .with(userHttpBasic(ADMIN)));

        Restaurant restaurant = readFromJson(action, Restaurant.class);
        Integer id = restaurant.getId();
        newRestaurant.setId(id);
        RESTAURANT_MATCHER.assertMatch(restaurant, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(id), newRestaurant);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.jsonMatcher(PLACE_1, PLACE_2, PLACE_3));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PLACE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.jsonMatcher(PLACE_1));
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
        perform(MockMvcRequestBuilders.get(REST_URL + PLACE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PLACE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print());

        assertThrows(NotFoundException.class, () -> restaurantService.get(PLACE_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdatedRestaurant();
        perform(MockMvcRequestBuilders.put(REST_URL + PLACE_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(restaurantService.get(PLACE_1_ID), updated);
    }

    @Test
    void registerInvalid() throws Exception {
        Restaurant restaurant = new Restaurant(null, "f");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant restaurant = new Restaurant(PLACE_1_ID, "f");
        perform(MockMvcRequestBuilders.put(REST_URL + PLACE_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Restaurant restaurant = new Restaurant(PLACE_1_ID, "<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + PLACE_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValues(restaurant))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }
}