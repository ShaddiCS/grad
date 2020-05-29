package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import topjava.grad.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
