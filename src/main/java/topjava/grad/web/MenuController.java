package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;
import topjava.grad.domain.to.MenuTo;
import topjava.grad.service.MenuService;

import java.time.LocalDate;
import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequiredArgsConstructor
@RequestMapping(MenuController.REST_URL)
public class MenuController {
    private final MenuService menuService;
    final static String REST_URL = "/rest/menus";

    @GetMapping("/by-place")
    public List<Menu> list(@RequestParam("restaurant") Restaurant restaurant) {
        return menuService.findAllByRestaurant(restaurant);
    }

    @GetMapping("/by-date")
    public List<Menu> list(@RequestParam("date") LocalDate date) {
        return menuService.findAllByDate(date);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Menu create(@RequestBody MenuTo menuTo) {
        checkNew(menuTo);
        return menuService.create(menuTo);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public void update(@RequestBody MenuTo menuTo, @PathVariable Integer id) {
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
