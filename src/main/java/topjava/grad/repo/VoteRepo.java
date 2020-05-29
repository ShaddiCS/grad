package topjava.grad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import topjava.grad.domain.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:id")
    List<Vote> getRestaurantVotes(@Param("id") Long id);

    @Query("SELECT COUNT(v.user) FROM Vote v WHERE v.restaurant.id = ?1 AND v.voteDate = ?2")
    Integer countVotes(Long id, LocalDate voteDate);
}
