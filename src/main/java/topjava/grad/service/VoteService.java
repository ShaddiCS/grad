package topjava.grad.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import topjava.grad.domain.Vote;
import topjava.grad.repo.RestaurantRepo;
import topjava.grad.repo.VoteRepo;
import topjava.grad.to.VoteTo;
import topjava.grad.util.UserMock;
import topjava.grad.util.UserUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoteService {
    private final VoteRepo voteRepo;
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public VoteService(VoteRepo voteRepo, RestaurantRepo restaurantRepo) {
        this.voteRepo = voteRepo;
        this.restaurantRepo = restaurantRepo;
    }

    public List<Vote> getRestaurantVotes(Long id) {
        return voteRepo.getRestaurantVotes(id);
    }

    public VoteTo vote(Long id) {
        if (UserUtil.checkCanVote(UserMock.USER)) {
            Vote saved = voteRepo.save(new Vote(restaurantRepo.getOne(id), UserMock.USER));
            return new VoteTo(saved.getRestaurant(), voteRepo.countVotes(id, LocalDate.now()));
        }
        return null;
    }
}
