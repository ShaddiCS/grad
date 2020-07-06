package topjava.grad.data;

import topjava.grad.TestMatcher;
import topjava.grad.domain.Vote;

import java.time.LocalDate;

import static topjava.grad.data.MenuTestData.*;
import static topjava.grad.data.UserTestData.ADMIN;
import static topjava.grad.data.UserTestData.USER;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = new TestMatcher<>("user", "menu");

    public static final Integer VOTE_1_ID = 100014;
    public static final Integer VOTE_2_ID = 100015;

    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, MENU_1, ADMIN, LocalDate.now());
    public static final Vote VOTE_2 = new Vote(VOTE_2_ID, MENU_3, USER, LocalDate.now().minusDays(1));

    public static Vote getNewVote() {
        return new Vote(null, MENU_1, USER);
    }

    public static Vote getUpdatedVote() {
        return new Vote(100014, MENU_2, ADMIN);
    }
}
