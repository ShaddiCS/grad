package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import topjava.grad.domain.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

}
