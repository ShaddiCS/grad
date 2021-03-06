package topjava.grad.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import topjava.grad.domain.User;
import topjava.grad.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static topjava.grad.util.ValidationUtil.assureIdConsistent;
import static topjava.grad.util.ValidationUtil.checkNew;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.REST_URL)
public class UserController {
    static final String REST_URL = "/rest/users";
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
        checkNew(user);
        log.info("create {}", user);

        User created = userService.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@PathVariable Integer id,@Valid @RequestBody User user) {
        assureIdConsistent(user, id);
        log.info("update {} with id={}", user, id);
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Integer id) {
        log.info("delete with id={}", id);
        userService.delete(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> list() {
        log.info("get all");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User get(@PathVariable Integer id) {
        log.info("get with id={}", id);
        return userService.get(id);
    }

    @GetMapping("/profile")
    public User getProfile(@Valid @AuthenticationPrincipal User user) {
        log.info("get profile");
        return userService.get(user.getId());
    }
}
