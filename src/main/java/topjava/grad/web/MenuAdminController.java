package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;
import topjava.grad.service.MenuService;

import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequiredArgsConstructor
@RequestMapping(MenuAdminController.REST_URL)
public class MenuAdminController {
    private final MenuService menuService;
    final static String REST_URL = "/rest/admin/restaurant/{restaurantId}/menu";

    @GetMapping("/by_place")
    public List<Menu> list(@PathVariable("restaurnatId") Restaurant restaurant) {
        return menuService.findAllByRestaurant(restaurant);
    }

    @PostMapping
    public Menu create(@PathVariable("restaurantId") Integer id, @RequestBody Menu menu) {
        checkNew(menu);
        return menuService.create(menu, id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Menu menu, @PathVariable Integer id, @PathVariable Integer restaurantId) {
        assureIdConsistent(menu, id);
        menuService.update(menu, restaurantId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        menuService.delete(id, restaurantId);
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable Integer restaurantId, @PathVariable Integer id) {
        return menuService.getWithDishes(id, restaurantId);
    }


}
