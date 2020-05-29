package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepo extends JpaRepository<Dish, Long> {
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAll(@Param("restaurantId") Long restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    Dish get(@Param("id")Long id, @Param("restaurantId") Long restaurantId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(@Param("id") Long id, @Param("restaurantId") Long restaurantId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant r WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    Dish getWithPlace(@Param("id") Long id, @Param("restaurantId") Long placeId);
}
