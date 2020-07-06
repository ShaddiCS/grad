package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.RestaurantService;

import java.net.URI;
import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(RestaurantController.REST_URL)
@RequiredArgsConstructor
public class RestaurantController {
    final static String REST_URL = "/rest/restaurants";
    private final RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Restaurant> register(@RequestBody Restaurant restaurant) {
        checkNew(restaurant);

        Restaurant created = restaurantService.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public void update(@RequestBody Restaurant restaurant, @PathVariable Integer id) {
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable("id") Restaurant restaurant) {
        return restaurant;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        restaurantService.delete(id);
    }
}
