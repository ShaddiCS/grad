package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import topjava.grad.domain.User;
import topjava.grad.domain.Vote;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepo extends JpaRepository<Vote, Integer> {
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.menu.id=?1 and v.voteDate=?2")
    Integer getVoteCount(Integer menuId, LocalDate date);
    @Query("SELECT v FROM Vote v JOIN FETCH v.user u JOIN FETCH u.roles JOIN FETCH v.menu m WHERE v.user=?1 and v.voteDate=?2")
    Optional<Vote> getByUser(User user, LocalDate date);
    @Query("SELECT v FROM Vote v JOIN FETCH v.menu m JOIN FETCH m.restaurant r WHERE v.user=?1 AND v.voteDate=?2")
    Optional<Vote> getWithRestaurant(User user, LocalDate data);
}
