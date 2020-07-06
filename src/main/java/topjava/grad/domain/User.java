package topjava.grad.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import topjava.grad.Views;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractBaseEntity implements UserDetails {
    @JsonView(Views.EmailPassword.class)
    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonView(Views.EmailPassword.class)
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    @OrderBy("voteDate DESC")
    private List<Vote> votes;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles;

    public User(Integer id, String email, String password, Role role, Role...roles) {
        super(id);
        this.email = email;
        this.password = password;
        setRoles(role, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public void setRoles(Role role, Role... roles) {
        this.roles = EnumSet.of(role, roles);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
