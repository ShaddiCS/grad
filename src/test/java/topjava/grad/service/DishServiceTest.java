package topjava.grad.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import topjava.grad.domain.Dish;
import topjava.grad.domain.to.DishTo;
import topjava.grad.repo.DishRepo;
import topjava.grad.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static topjava.grad.data.DishTestData.*;

@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
@SpringBootTest
class DishServiceTest {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishRepo dishRepo;

    @Test
    void create() {
        DishTo newDish = getNewDish();
        Dish created = dishService.create(newDish);
        newDish.setId(created.getId());

        DISH_MATCHER.assertMatch(created, dishService.createFromTo(newDish));
    }

    @Test
    void delete() {
        dishService.delete(DISH_1_ID);
        Assertions.assertNull(dishRepo.findById(DISH_1_ID).orElse(null));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.delete(1));
    }

    @Test
    void update() {
        DishTo dish = getUpdatedDish();
        dishService.update(dish);
        DISH_MATCHER.assertMatch(dishService.get(DISH_1_ID), dishService.createFromTo(dish));
    }

    @Test
    void get() {
        DISH_MATCHER.assertMatch(dishService.get(DISH_1_ID), DISH_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.get(1));
    }
}