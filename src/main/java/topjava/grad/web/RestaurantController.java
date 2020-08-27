package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.RestaurantService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(RestaurantController.REST_URL)
@RequiredArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/rest/restaurants";
    private final RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Restaurant> register(@Valid @RequestBody Restaurant restaurant) {
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable Integer id) {
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable("id") Integer id) {
        return restaurantService.get(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        restaurantService.delete(id);
    }
}
