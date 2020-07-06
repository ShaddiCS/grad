package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import topjava.grad.domain.Menu;
import topjava.grad.domain.Restaurant;
import topjava.grad.domain.User;
import topjava.grad.domain.Vote;
import topjava.grad.service.MenuService;
import topjava.grad.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RootController {

    private final MenuService menuService;
    private final VoteService voteService;

    @GetMapping("/rest/menus")
    public List<Menu> list(@RequestParam LocalDate date) {
        return menuService.findAllByDate(date);
    }

    @GetMapping("/rest/vote")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal User user) {
        Vote vote = voteService.get(user, LocalDate.now());
        if (vote == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vote.getMenu().getRestaurant());
    }
}
