package topjava.grad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Restaurant;
import topjava.grad.repo.RestaurantRepo;
import topjava.grad.util.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepo.findAll();
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(Integer id) {
        if (restaurantRepo.delete(id) == 0) {
            throw new NotFoundException("id=" + id);
        }
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        restaurantRepo.save(restaurant);
    }

    public Restaurant get(Integer id) {
        return restaurantRepo.findById(id).orElseThrow(() -> new NotFoundException("id=" + id));
    }
}
