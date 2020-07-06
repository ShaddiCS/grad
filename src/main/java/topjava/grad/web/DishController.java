package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import topjava.grad.domain.Dish;
import topjava.grad.domain.to.DishTo;
import topjava.grad.service.DishService;

import java.net.URI;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(DishController.REST_URL)
@RequiredArgsConstructor
public class DishController {
    final static String REST_URL = "/rest/dishes";
    private final DishService dishService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Dish> create(@RequestBody DishTo dishTo) {
        checkNew(dishTo);
        Dish created = dishService.create(dishTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Integer id) {
        dishService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@RequestBody DishTo dishTo, @PathVariable Integer id) {
        assureIdConsistent(dishTo, id);
        dishService.update(dishTo);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable Integer id) {
        return dishService.get(id);
    }
}
