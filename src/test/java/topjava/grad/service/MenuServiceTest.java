package topjava.grad.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import topjava.grad.data.MenuTestData;
import topjava.grad.domain.Menu;
import topjava.grad.repo.MenuRepo;
import topjava.grad.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static topjava.grad.data.DishTestData.*;
import static topjava.grad.data.MenuTestData.*;
import static topjava.grad.data.RestaurantTestData.*;

@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepo menuRepo;

    @Test
    void create() {
        Menu newMenu = getNew();
        Menu created = menuService.create(newMenu, PLACE_1_ID);
        newMenu.setId(created.getId());

        MENU_MATCHER.assertMatch(created, newMenu);
    }

    @Test
    void findAllByRestaurant() {
        List<Menu> menus = menuService.findAllByRestaurant(PLACE_1);
        MENU_MATCHER.assertMatch(menus, Collections.singletonList(MENU_1));
        DISH_MATCHER.assertMatch(menus.get(0).getDishes(), DISHES_1);
    }

    @Test
    void findAllByDate() {
        List<Menu> menus = menuService.findAllByDate(LocalDate.now());
        MENU_MATCHER.assertMatch(menus, List.of(MENU_1, MENU_2));
        DISH_MATCHER.assertMatch(menus.get(0).getDishes(), DISHES_1);
        DISH_MATCHER.assertMatch(menus.get(1).getDishes(), DISHES_2);
        RESTAURANT_MATCHER.assertMatch(menus.get(0).getRestaurant(), PLACE_1);
        RESTAURANT_MATCHER.assertMatch(menus.get(1).getRestaurant(), PLACE_2);
    }

    @Test
    void update() {
        Menu menu = getUpdated();
        menuService.update(menu, PLACE_1_ID);
        MENU_MATCHER.assertMatch(menuService.getWithDishes(MENU_1_ID, PLACE_1_ID), menu);
    }

    @Test
    void updateNotFound() {
        assertThrows(NotFoundException.class,
                () -> menuService.update(MENU_1, PLACE_2_ID));
    }

    @Test
    void delete() {
        menuService.delete(MENU_1_ID, PLACE_1_ID);
        Assertions.assertNull(menuRepo.get(MENU_1_ID, PLACE_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class,
                () -> menuService.delete(1, PLACE_1_ID));
    }

    @Test
    void deleteWrongRestaurant() {
        assertThrows(NotFoundException.class,
                () -> menuService.delete(MENU_2_ID, PLACE_1_ID));
    }

    @Test
    void get() {
        Menu actual = menuService.get(MENU_1_ID, PLACE_1_ID);
        MENU_MATCHER.assertMatch(actual, MENU_1);
    }

    @Test
    void getWithDishes() {
        Menu actual = menuService.getWithDishes(MENU_1_ID, PLACE_1_ID);
        MENU_MATCHER.assertMatch(actual, MENU_1);
        DISH_MATCHER.assertMatch(actual.getDishes(), DISHES_1);
    }

    @Test
    void getWithDishesNotFound() {
        assertThrows(NotFoundException.class,
                () -> menuService.getWithDishes(1, PLACE_1_ID));
    }

    @Test
    void getWithDishesWrongRestaurant() {
        assertThrows(NotFoundException.class,
                () -> menuService.getWithDishes(MENU_2_ID, PLACE_1_ID));
    }


}