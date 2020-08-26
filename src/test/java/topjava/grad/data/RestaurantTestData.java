package topjava.grad.data;

import topjava.grad.TestMatcher;
import topjava.grad.domain.Restaurant;

import java.util.List;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = new TestMatcher<>(Restaurant.class);

    public static final int PLACE_1_ID = 100002;
    public static final int PLACE_2_ID = 100003;
    public static final int PLACE_3_ID = 100004;

    public static final Restaurant PLACE_1 = new Restaurant(PLACE_1_ID, "First place");
    public static final Restaurant PLACE_2 = new Restaurant(PLACE_2_ID, "Second place");
    public static final Restaurant PLACE_3 = new Restaurant(PLACE_3_ID, "Third place");

    public static final List<Restaurant> RESTAURANTS = List.of(PLACE_1, PLACE_2, PLACE_3);

    public static Restaurant getNewRestaurant() {
        return new Restaurant(null, "Test place");
    }

    public static Restaurant getUpdatedRestaurant() {
        return new Restaurant(PLACE_1_ID, "Changed name");
    }
}
