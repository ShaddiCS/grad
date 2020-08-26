package topjava.grad.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepo extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant=?1")
    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Menu> getAllByRestaurant(Restaurant restaurant);

    @Query("SELECT m FROM Menu m WHERE m.date=?1")
    @EntityGraph(attributePaths = {"dishes", "restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Menu> getAllByDate(LocalDate date);

    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=?1")
    Integer delete(Integer id);

    @Query("SELECT m FROM Menu m WHERE m.id=?1")
    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.FETCH)
    Menu getWithDishes(Integer id);

    @Query("SELECT m FROM Menu m WHERE m.id=?1")
    Menu get(Integer id);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=?1 and m.date=?2")
    Menu getByDate(Integer restaurantId, LocalDate date);
}
