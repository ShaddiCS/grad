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
        Menu menu = menuRepo.getByDate(restaurantId, LocalDate.now());

        if (menu == null) {
            throw new NotFoundException("restaurantId=" + restaurantId);
        }

        Vote vote = voteRepo.getByUser(user, LocalDate.now()).orElse(null);

        if (vote == null) {
            return false;
        }

        vote.setMenu(menu);
        return true;
    }

    @Transactional
    public boolean vote(Integer restaurantId, User user) {
        Menu menu = menuRepo.getByDate(restaurantId, LocalDate.now());

        if (menu == null) {
            throw new NotFoundException("restaurantId=" + restaurantId);
        }

        if (voteRepo.getByUser(user, LocalDate.now()).isPresent()) {
            return false;
        }

        voteRepo.save(new Vote(menu, user));
        return true;
    }

    public Vote get(User user, LocalDate date) {
        return voteRepo.getByUser(user, date).orElseThrow(() -> new NotFoundException("user=" + user.getUsername()));
    }

    public Vote getWithRestaurant(User user, LocalDate date) {
        return voteRepo.getWithRestaurant(user, date).orElseThrow(() -> new NotFoundException("user=" + user.getUsername()));
    }
}
