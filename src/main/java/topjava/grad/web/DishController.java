package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import topjava.grad.domain.Dish;
import topjava.grad.domain.to.DishTo;
import topjava.grad.service.DishService;

import javax.validation.Valid;
import java.net.URI;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(DishController.REST_URL)
@RequiredArgsConstructor
public class DishController {
    static final String REST_URL = "/rest/dishes";
    private final DishService dishService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo) {
        checkNew(dishTo);
        log.info("create from to {}", dishTo);

        Dish created = dishService.create(dishTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Integer id) {
        log.info("delete with id={}", id);
        dishService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable Integer id) {
        assureIdConsistent(dishTo, id);
        log.info("update from to {} with id={}", dishTo, id);
        dishService.update(dishTo);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable Integer id) {
        log.info("get with id={}", id);
        return dishService.get(id);
    }
}
