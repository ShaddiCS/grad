package topjava.grad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Restaurant;
import topjava.grad.repo.RestaurantRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    public List<Restaurant> getAll() {
        return restaurantRepo.findAll();
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    public void delete(Integer id) {
        restaurantRepo.deleteById(id);
    }

    @Transactional
    public void update(Restaurant restaurant, Restaurant restaurantFromDb) {
        BeanUtils.copyProperties(restaurant, restaurantFromDb);
    }

    public Restaurant get(Integer id) {
        return restaurantRepo.findById(id).orElse(null);
    }
}
