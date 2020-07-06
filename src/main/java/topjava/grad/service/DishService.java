package topjava.grad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Dish;
import topjava.grad.domain.Menu;
import topjava.grad.repo.DishRepo;
import topjava.grad.repo.MenuRepo;
import topjava.grad.util.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepo dishRepo;
    private final MenuRepo menuRepo;

    @Transactional
    public Dish create(Dish dish, Integer menuId) {
        dish.setMenu(menuRepo.getOne(menuId));
        return dishRepo.save(dish);
    }

    @Transactional
    public void delete(Integer id) {
        if (dishRepo.delete(id) == 0) {
            throw new NotFoundException("id=" + id);
        }
    }

    @Transactional
    public void update(Dish dish, Integer menuId) {
        dish.setMenu(menuRepo.getOne(menuId));
        dishRepo.save(dish);
    }

    public Dish get(Integer id) {
        return dishRepo.findById(id).orElseThrow(() -> new NotFoundException("id=" + id));
    }
}
