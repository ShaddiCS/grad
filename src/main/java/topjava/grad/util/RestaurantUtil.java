package topjava.grad.util;

import topjava.grad.domain.Restaurant;
import topjava.grad.service.VoteService;
import topjava.grad.to.RestaurantTo;

public class RestaurantUtil {
    private static VoteService voteService;

    public static RestaurantTo asTo(Restaurant restaurant) {
        int votes = voteService.getRestaurantVotes(restaurant.getId()).size();
        return new RestaurantTo(restaurant.getName(), restaurant.getDishes(), votes);
    }
}
