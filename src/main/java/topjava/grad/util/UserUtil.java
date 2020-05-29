package topjava.grad.util;

import topjava.grad.domain.User;
import topjava.grad.domain.Vote;
import topjava.grad.to.UserTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UserUtil {

    public static UserTo asTo(User user) {
        return new UserTo(user.getEmail(), user.getPassword(), checkCanVote(user));
    }

    public static boolean checkCanVote(User user) {
        List<Vote> votes = user.getVotes();

        if(LocalTime.now().isBefore(LocalTime.of(11, 0)) || votes.size() == 0) {
            return true;
        } else {
            Vote todayVote = user.getVotes().stream()
                    .filter(vote -> !vote.getVoteDate().equals(LocalDate.now()))
                    .findAny()
                    .orElse(null);
            return todayVote == null;
        }
    }
}
