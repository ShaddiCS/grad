package topjava.grad.to;

import lombok.Data;
import topjava.grad.domain.Restaurant;

@Data
public class VoteTo {
    private final Restaurant restaurant;
    private final Integer voteCount;
}
