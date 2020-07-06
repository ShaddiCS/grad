package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.MenuService;

import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequiredArgsConstructor
@RequestMapping(MenuController.REST_URL)
public class MenuController {
    private final MenuService menuService;
    final static String REST_URL = RestaurantController.REST_URL + "/{restaurantId}/menus";

    @GetMapping("/by_place")
    public List<Menu> list(@PathVariable("restaurnatId") Restaurant restaurant) {
        return menuService.findAllByRestaurant(restaurant);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Menu create(@PathVariable("restaurantId") Integer restaurantId, @RequestBody Menu menu) {
        checkNew(menu);
        return menuService.create(menu, restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public void update(@RequestBody Menu menu, @PathVariable Integer id, @PathVariable Integer restaurantId) {
        assureIdConsistent(menu, id);
        menuService.update(menu, restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        menuService.delete(id);
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        return menuService.getWithDishes(id);
    }


}
