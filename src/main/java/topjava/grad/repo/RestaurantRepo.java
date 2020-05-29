package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Restaurant;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.dishDate=:dishDate AND r.id=:id")
    Restaurant getByMenuDate(@Param("id") Long id, @Param("dishDate") LocalDate dishDate);
}
