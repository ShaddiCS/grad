package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import topjava.grad.domain.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1")
    Integer delete(Integer id);
}
