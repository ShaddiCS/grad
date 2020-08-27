package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;
import topjava.grad.domain.to.MenuTo;
import topjava.grad.service.MenuService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequiredArgsConstructor
@RequestMapping(MenuController.REST_URL)
public class MenuController {
    private final MenuService menuService;
    static final String REST_URL = "/rest/menus";

    @GetMapping
    public List<Menu> list() {
        return menuService.findAllByDate(LocalDate.now());
    }

    @GetMapping("/by-place")
    public List<Menu> getAllByPlace(@Valid @RequestParam("restaurant") Restaurant restaurant) {
        return menuService.findAllByRestaurant(restaurant);
    }

    @GetMapping("/by-date")
    public List<Menu> getAllByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return menuService.findAllByDate(date);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Menu create(@Valid @RequestBody MenuTo menuTo) {
        checkNew(menuTo);
        return menuService.create(menuTo);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable Integer id) {
        assureIdConsistent(menuTo, id);
        menuService.update(menuTo);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        menuService.delete(id);
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable Integer id) {
        return menuService.getWithDishes(id);
    }


}
