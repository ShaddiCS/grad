package topjava.grad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import topjava.grad.domain.Role;
import topjava.grad.domain.User;
import topjava.grad.repo.UserRepo;
import topjava.grad.util.exception.NotFoundException;

import java.util.EnumSet;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return user;
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User get(Integer id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("id=" + id));
    }

    @Transactional
    public void delete(Integer id) {
        if (userRepo.delete(id) == 0) {
            throw new NotFoundException("id=" + id);
        }
    }

    @Transactional
    public void update(User user) {
        prepareAndSave(user);
    }

    @Transactional
    public User create(User user) {
        user.setRoles(EnumSet.of(Role.USER));
        return prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        String password = user.getPassword();

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setEmail(user.getEmail().toLowerCase());

        return userRepo.save(user);
    }
}
