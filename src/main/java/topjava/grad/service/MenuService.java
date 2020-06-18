package topjava.grad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;
import topjava.grad.repo.MenuRepo;
import topjava.grad.repo.RestaurantRepo;
import topjava.grad.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepo menuRepo;
    private final RestaurantRepo restaurantRepo;

    @Transactional
    public Menu create(Menu menu, Integer restaurantId) {
        menu.setRestaurant(restaurantRepo.getOne(restaurantId));
        return menuRepo.save(menu);
    }

    public List<Menu> findAllByDate(LocalDate date) {
        return menuRepo.getAllByDate(date);
    }

    public List<Menu> findAllByRestaurant(Restaurant restaurant) {
        return menuRepo.getAllByRestaurant(restaurant);
    }

    @Transactional
    public void update(Menu menu, Integer restaurantId) {
        Integer menuId = menu.getId();

        if (menuRepo.get(menuId, restaurantId) == null) {
            throw new NotFoundException("id=" + menuId);
        }

        menu.setRestaurant(restaurantRepo.getOne(restaurantId));
        menuRepo.save(menu);
    }

    @Transactional
    public void delete(Integer id, Integer restaurantId) {
        if (menuRepo.delete(id, restaurantId) == 0) {
            throw new NotFoundException("id=" + id);
        }
    }

    public Menu get(Integer id, Integer restaurantId) {
        Menu menu = menuRepo.get(id, restaurantId);
        if (menu == null) {
            throw new NotFoundException("id=" + id);
        }

        return menu;
    }

    public Menu getWithDishes(Integer id, Integer restaurantId) {
        Menu menu = menuRepo.getWithDishes(id, restaurantId);
        if (menu == null) {
            throw new NotFoundException("id=" + id);
        }

        return menu;
    }
}
