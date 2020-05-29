package topjava.grad.to;

import lombok.Data;
import topjava.grad.domain.Dish;

import java.util.List;

@Data
public class RestaurantTo {
    private final String name;
    private final List<Dish> dishes;
    private final Integer votes;
}
