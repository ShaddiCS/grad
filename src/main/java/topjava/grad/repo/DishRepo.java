package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import topjava.grad.domain.Dish;
import topjava.grad.domain.Menu;

import java.util.List;

public interface DishRepo extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d WHERE d.menu=?1")
    List<Dish> getByMenu(Menu menu);

    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1")
    Integer delete(Integer id);
}
