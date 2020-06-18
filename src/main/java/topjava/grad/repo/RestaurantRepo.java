package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import topjava.grad.domain.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {

    Restaurant getById(Integer id);

    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id =?1")
    Integer delete(Integer id);
}
