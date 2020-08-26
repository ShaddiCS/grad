package topjava.grad.data;

import topjava.grad.TestMatcher;
import topjava.grad.domain.Dish;
import topjava.grad.domain.to.DishTo;

import java.util.List;

import static topjava.grad.data.MenuTestData.MENU_1_ID;

public class DishTestData {
    public static final TestMatcher<Dish> DISH_MATCHER = new TestMatcher<>(Dish.class, "menu");

    public static final int DISH_1_ID = 100008;
    public static final int DISH_2_ID = 100009;
    public static final int DISH_3_ID = 100010;
    public static final int DISH_4_ID = 100011;
    public static final int DISH_5_ID = 100012;

    public static final Dish DISH_1 = new Dish(DISH_1_ID, "First main", 100);
    public static final Dish DISH_2 = new Dish(DISH_2_ID, "First best", 50);
    public static final Dish DISH_3 = new Dish(DISH_3_ID, "First cheap", 10);
    public static final Dish DISH_4 = new Dish(DISH_4_ID, "Second main", 1000);
    public static final Dish DISH_5 = new Dish(DISH_5_ID, "Second cheap", 200);

    public static final List<Dish> DISHES_1 = List.of(DISH_1, DISH_2, DISH_3);
    public static final List<Dish> DISHES_2 = List.of(DISH_4, DISH_5);

    public static DishTo getNewDish() {
        return new DishTo(null, "New Dish", 333, MENU_1_ID);
    }

    public static DishTo getUpdatedDish() {
        return new DishTo(DISH_1_ID, "Updated dish", 333, MENU_1_ID);
    }
}
