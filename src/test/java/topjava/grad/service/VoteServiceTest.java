package topjava.grad.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import topjava.grad.domain.User;
import topjava.grad.domain.Vote;
import topjava.grad.repo.VoteRepo;
import topjava.grad.util.exception.NotFoundException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static topjava.grad.data.MenuTestData.MENU_1;
import static topjava.grad.data.MenuTestData.MENU_MATCHER;
import static topjava.grad.data.RestaurantTestData.*;
import static topjava.grad.data.UserTestData.*;
import static topjava.grad.data.VoteTestData.*;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteRepo voteRepo;

    @Test
    void updateVote() {
        Vote updatedVote = getUpdatedVote();
        User user = updatedVote.getUser();

        voteService.updateVote(PLACE_2_ID, user);

        Vote vote = voteService.get(user, LocalDate.now());

        VOTE_MATCHER.assertMatch(vote, updatedVote);
        USER_MATCHER.assertMatch(vote.getUser(), user);
        MENU_MATCHER.assertMatch(vote.getMenu(), updatedVote.getMenu());
    }

    @Test
    void updateVoteNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.updateVote(PLACE_3_ID, USER));
    }

    @Test
    void vote() {
        Vote newVote = getNewVote();
        User user = newVote.getUser();
        voteService.vote(PLACE_1_ID, user);
        Vote created = voteRepo.getByUser(user, LocalDate.now());
        newVote.setId(created.getId());

        VOTE_MATCHER.assertMatch(created, newVote);
        USER_MATCHER.assertMatch(created.getUser(), user);
        MENU_MATCHER.assertMatch(created.getMenu(), newVote.getMenu());
    }

    @Test
    void voteMenuNotFound() {
        assertThrows(NotFoundException.class,() -> voteService.vote(PLACE_3_ID, USER));
    }

    @Test
    void voteDuplicate() {
        assertFalse(voteService.vote(PLACE_2_ID, ADMIN));
    }

    @Test
    void get() {
        Vote vote = voteService.get(ADMIN, LocalDate.now());
        assertEquals(vote.getId(), VOTE_1_ID);
        USER_MATCHER.assertMatch(vote.getUser(), ADMIN);
        MENU_MATCHER.assertMatch(vote.getMenu(), MENU_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> voteService.get(USER, LocalDate.now()));
    }
}