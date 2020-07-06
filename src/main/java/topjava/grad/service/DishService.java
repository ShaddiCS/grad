package topjava.grad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Dish;
import topjava.grad.domain.to.DishTo;
import topjava.grad.repo.DishRepo;
import topjava.grad.repo.MenuRepo;
import topjava.grad.util.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepo dishRepo;
    private final MenuRepo menuRepo;

    @Transactional
    public Dish create(DishTo dishTo) {
        return dishRepo.save(createFromTo(dishTo));
    }

    @Transactional
    public void delete(Integer id) {
        if (dishRepo.delete(id) == 0) {
            throw new NotFoundException("id=" + id);
        }
    }

    @Transactional
    public void update(DishTo dishTo) {
        dishRepo.save(createFromTo(dishTo));
    }

    public Dish get(Integer id) {
        return dishRepo.findById(id).orElseThrow(() -> new NotFoundException("id=" + id));
    }

    public Dish createFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), menuRepo.getOne(dishTo.getMenuId()));
    }
}
