package topjava.grad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;
import topjava.grad.domain.to.MenuTo;
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
    public Menu create(MenuTo menuTo) {
        return menuRepo.save(createFromTo(menuTo));
    }

    public List<Menu> findAllByDate(LocalDate date) {
        return menuRepo.getAllByDate(date);
    }

    public List<Menu> findAllByRestaurant(Restaurant restaurant) {
        return menuRepo.getAllByRestaurant(restaurant);
    }

    @Transactional
    public void update(MenuTo menuTo) {
        menuRepo.save(createFromTo(menuTo));
    }

    @Transactional
    public void delete(Integer id) {
        if (menuRepo.delete(id) == 0) {
            throw new NotFoundException("id=" + id);
        }
    }

    public Menu get(Integer id) {
        Menu menu = menuRepo.get(id);
        if (menu == null) {
            throw new NotFoundException("id=" + id);
        }

        return menu;
    }

    public Menu getWithDishes(Integer id) {
        Menu menu = menuRepo.getWithDishes(id);
        if (menu == null) {
            throw new NotFoundException("id=" + id);
        }

        return menu;
    }

    public Menu createFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurantRepo.getOne(menuTo.getRestaurantId()));
    }
}
