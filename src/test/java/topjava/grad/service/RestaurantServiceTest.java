package topjava.grad.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import topjava.grad.domain.Restaurant;
import topjava.grad.repo.RestaurantRepo;
import topjava.grad.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static topjava.grad.data.RestaurantTestData.*;

@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Test
    void getAll() {
        List<Restaurant> actual = restaurantService.getAll();
        RESTAURANT_MATCHER.assertMatch(actual, RESTAURANTS);
    }

    @Test
    void create() {
        Restaurant newRestaurant = getNewRestaurant();
        Restaurant created = restaurantService.create(newRestaurant);

        Integer newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void delete() {
        restaurantService.delete(PLACE_1_ID);
        Assertions.assertNull(restaurantRepo.getById(PLACE_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class,
                () ->restaurantService.delete(1));
    }

    @Test
    void update() {
        Restaurant updated = getUpdatedRestaurant();
        restaurantService.update(updated);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(PLACE_1_ID), updated);
    }

    @Test
    void get() {
        Restaurant actual = restaurantService.get(PLACE_1_ID);
        RESTAURANT_MATCHER.assertMatch(actual, PLACE_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class,
                () -> restaurantService.get(1));
    }
}