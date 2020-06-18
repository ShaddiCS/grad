package topjava.grad.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant=?1")
    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Menu> getAllByRestaurant(Restaurant restaurant);

    @Query("SELECT m FROM Menu m WHERE m.date=?1")
    @EntityGraph(attributePaths = {"dishes", "restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Menu> getAllByDate(LocalDate date);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=?1 and m.restaurant.id=?2")
    Integer delete(Integer id, Integer restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.id=?1 AND m.restaurant.id=?2")
    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    Menu getWithDishes(Integer id, Integer restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.id=?1 AND m.restaurant.id=?2")
    Menu get(Integer id, Integer restaurantId);
}
