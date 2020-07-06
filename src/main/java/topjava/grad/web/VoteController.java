package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import topjava.grad.domain.User;
import topjava.grad.domain.Vote;
import topjava.grad.service.VoteService;

import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(VoteController.REST_URL)
public class VoteController {
    final static String REST_URL = "/rest/vote";
    private final static LocalTime TIME_LIMIT = LocalTime.of(11, 0);
    private final VoteService voteService;

    @PostMapping("/{id}")
    public ResponseEntity<Vote> vote(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        if (!voteService.vote(id, user)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vote> changeVote(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        if (LocalTime.now().isAfter(TIME_LIMIT) || !voteService.updateVote(id, user)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
