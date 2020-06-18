package topjava.grad.data;

import topjava.grad.TestMatcher;
import topjava.grad.domain.Restaurant;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = new TestMatcher<>();

    public static final int PLACE_1_ID = 100002;
    public static final int PLACE_2_ID = 100003;
    public static final int PLACE_3_ID = 100004;

    public static final Restaurant PLACE_1 = new Restaurant(PLACE_1_ID, "First place");
    public static final Restaurant PLACE_2 = new Restaurant(PLACE_2_ID, "Second place");
    public static final Restaurant PLACE_3 = new Restaurant(PLACE_3_ID, "Third place");
}
