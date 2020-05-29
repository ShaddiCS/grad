package topjava.grad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.DishService;
import topjava.grad.service.RestaurantService;

import java.net.URI;

@RestController
@RequestMapping("/rest/restaurant")
public class VoteSystemController {
    private final RestaurantService restaurantService;
    private final DishService dishService;

    @Autowired
    public VoteSystemController(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.create(restaurant);
        restaurant.getDishes().forEach(dish -> dishService.create(created.getId(), dish));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/restaurant" + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable Long id) {
        return restaurantService.get(id);
    }
}
