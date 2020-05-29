package topjava.grad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import topjava.grad.domain.Dish;
import topjava.grad.repo.DishRepo;
import topjava.grad.repo.RestaurantRepo;

import java.util.List;

import static topjava.grad.util.ValidationUtil.checkNotFound;

@Service
public class DishService {
    private final DishRepo dishRepo;
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public DishService(DishRepo dishRepo, RestaurantRepo restaurantRepo) {
        this.dishRepo = dishRepo;
        this.restaurantRepo = restaurantRepo;
    }

    public List<Dish> getAll(Long placeId) {
        return dishRepo.getAll(placeId);
    }

    public Dish get(Long placeId, Long id) {
        return checkNotFound(dishRepo.get(id, placeId), id);
    }

    public void delete(Long placeId, Long id) {
        checkNotFound(dishRepo.delete(id, placeId), id);
    }

    public Dish create(Long placeId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return save(placeId, dish);
    }

    public void update(Long placeId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFound(save(placeId, dish), dish.getId());
    }

    @Transactional
    Dish save(Long placeId, Dish dish) {
        if (!dish.isNew() && get(dish.getId(), placeId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepo.getOne(placeId));
        return dishRepo.save(dish);
    }

    public Dish getWithPlace(Long placeId, Long id) {
        return checkNotFound(dishRepo.getWithPlace(id, placeId), id);
    }
}
