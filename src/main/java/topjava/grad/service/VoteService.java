package topjava.grad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topjava.grad.domain.Menu;
import topjava.grad.domain.User;
import topjava.grad.domain.Vote;
import topjava.grad.repo.MenuRepo;
import topjava.grad.repo.VoteRepo;
import topjava.grad.util.exception.NotFoundException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepo voteRepo;
    private final MenuRepo menuRepo;

    @Transactional
    public boolean updateVote(Integer restaurantId, User user) {
        Vote voteFromDb = voteRepo.getByUser(user, LocalDate.now());
        Menu menu = menuRepo.getByDate(restaurantId, LocalDate.now());

        if (menu == null) {
            throw new NotFoundException("restaurantId=" + restaurantId);
        }

        if (voteFromDb == null) {
            return false;
        }

        voteFromDb.setMenu(menu);
        return true;
    }

    @Transactional
    public boolean vote(Integer restaurantId, User user) {
        Menu menu = menuRepo.getByDate(restaurantId, LocalDate.now());

        if (menu == null) {
            throw new NotFoundException("restaurantId=" + restaurantId);
        }

        Vote vote = voteRepo.getByUser(user, LocalDate.now());

        if (vote != null) {
            return false;
        }

        voteRepo.save(new Vote(null, menu, user));
        return true;
    }

    public Vote get(User user, LocalDate date) {
        Vote vote = voteRepo.getByUser(user, date);

        if (vote == null) {
            throw new NotFoundException("user=" + user.getUsername());
        }

        return vote;
    }
}
