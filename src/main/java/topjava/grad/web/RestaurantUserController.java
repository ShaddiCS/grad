package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.RestaurantService;

import java.util.List;

@RestController("/rest/restaurant")
@RequiredArgsConstructor
public class RestaurantUserController {
    private final RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }
}
