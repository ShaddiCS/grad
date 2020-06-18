package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import topjava.grad.domain.Dish;

public interface DishRepo extends JpaRepository<Dish, Integer> {
}
