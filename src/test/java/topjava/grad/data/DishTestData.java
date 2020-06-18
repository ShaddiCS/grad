package topjava.grad.data;

import topjava.grad.TestMatcher;
import topjava.grad.domain.Dish;

import java.util.List;

public class DishTestData {
    public static final TestMatcher<Dish> DISH_MATCHER = new TestMatcher<>();

    public static final int DISH_1_ID = 100008;
    public static final int DISH_2_ID = 100009;
    public static final int DISH_3_ID = 100010;
    public static final int DISH_4_ID = 100011;
    public static final int DISH_5_ID = 100012;

    public static final Dish DISH_1 = new Dish(DISH_1_ID, "First main", 100.5);
    public static final Dish DISH_2 = new Dish(DISH_2_ID, "First best", 50.3);
    public static final Dish DISH_3 = new Dish(DISH_3_ID, "First cheap", 10.4);
    public static final Dish DISH_4 = new Dish(DISH_4_ID, "Second main", 1000.9);
    public static final Dish DISH_5 = new Dish(DISH_5_ID, "Second cheap", 200.0);

    public static final List<Dish> DISHES_1 = List.of(DISH_1, DISH_2, DISH_3);
    public static final List<Dish> DISHES_2 = List.of(DISH_4, DISH_5);
}
