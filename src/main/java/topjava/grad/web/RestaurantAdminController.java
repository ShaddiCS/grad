package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequestMapping("/rest/admin/restaurant")
@RequiredArgsConstructor
public class RestaurantAdminController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        checkNew(restaurant);

        Restaurant created = restaurantService.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/restaurant" + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Restaurant restaurant, @PathVariable Integer id) {
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable("id") Restaurant restaurant) {
        return restaurant;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        restaurantService.delete(id);
    }
}
