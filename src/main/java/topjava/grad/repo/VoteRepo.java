package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import topjava.grad.domain.User;
import topjava.grad.domain.Vote;

import java.time.LocalDate;

public interface VoteRepo extends JpaRepository<Vote, Integer> {
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.menu.id=?1 and v.voteDate=?2")
    Integer getVoteCount(Integer menuId, LocalDate date);
    @Query("SELECT v FROM Vote v JOIN FETCH v.user u JOIN FETCH u.roles JOIN FETCH v.menu m WHERE v.user=?1 and v.voteDate=?2")
    Vote getByUser(User user, LocalDate date);
}
