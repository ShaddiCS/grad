package topjava.grad.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import topjava.grad.domain.Vote;
import topjava.grad.service.VoteService;
import topjava.grad.util.TimeMachine;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.grad.TestUtil.userHttpBasic;
import static topjava.grad.data.MenuTestData.*;
import static topjava.grad.data.RestaurantTestData.*;
import static topjava.grad.data.UserTestData.*;
import static topjava.grad.data.VoteTestData.VOTE_MATCHER;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + "/";
    private static final String UPDATE_ALLOWED = LocalDate.now().toString() + "T10:00:00.00Z";
    private static final String UPDATE_FORBIDDEN = LocalDate.now().toString() + "T11:00:01.00Z";

    @Autowired
    private VoteService voteService;

    @Autowired
    private TimeMachine timeMachine;

    @AfterEach
    void afterEach() {
        timeMachine.setDefaultClock();
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + PLACE_1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk());

        Vote actual = voteService.get(USER, LocalDate.now());
        Vote expected = new Vote(actual.getId(), MENU_1, USER);
        VOTE_MATCHER.assertMatch(actual, expected);
        MENU_MATCHER.assertMatch(actual.getMenu(), MENU_1);
        USER_MATCHER.assertMatch(actual.getUser(), USER);
    }

    @Test
    void voteUnauth() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + PLACE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void voteConflict() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + PLACE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isConflict());
    }

    @Test
    void voteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + 1)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotFound());
    }

    @Test
    void changeVote() throws Exception {
        timeMachine.setFixedClockAt(UPDATE_ALLOWED);

        perform(MockMvcRequestBuilders.put(REST_URL + PLACE_2_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());

        Vote actual = voteService.get(ADMIN, LocalDate.now());
        Vote expected = new Vote(actual.getId(), MENU_2, ADMIN);
        VOTE_MATCHER.assertMatch(actual, expected);
        MENU_MATCHER.assertMatch(actual.getMenu(), MENU_2);
        USER_MATCHER.assertMatch(actual.getUser(), ADMIN);
    }

    @Test
    void changeVoteUnauth() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + PLACE_2_ID))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void changeVoteTooLate() throws Exception {
        timeMachine.setFixedClockAt(UPDATE_FORBIDDEN);

        perform(MockMvcRequestBuilders.put(REST_URL + PLACE_2_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void changeVoteNotFound() throws Exception {
        timeMachine.setFixedClockAt(UPDATE_ALLOWED);

        perform(MockMvcRequestBuilders.put(REST_URL + PLACE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotFound());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.jsonMatcher(PLACE_1));
    }
}