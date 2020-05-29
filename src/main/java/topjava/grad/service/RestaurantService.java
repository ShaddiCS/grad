package topjava.grad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Restaurant;
import topjava.grad.repo.RestaurantRepo;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public RestaurantService(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    public List<Restaurant> getAll() {
        return restaurantRepo.findAll();
    }

    public Restaurant create(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    public void delete(Long id) {
        restaurantRepo.deleteById(id);
    }

    public void update(Restaurant restaurant) {
        restaurantRepo.save(restaurant);
    }

    public Restaurant get(Long id) {
        return restaurantRepo.getByMenuDate(id, LocalDate.now());
    }
}
