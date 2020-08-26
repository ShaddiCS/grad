package topjava.grad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "user_unique_date_idx"))
public class Vote extends AbstractBaseEntity{

    @Column(name = "vote_date")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate voteDate;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    private Menu menu;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Vote(Menu menu, User user) {
        this(null, menu, user);
    }

    public Vote(Integer id, Menu menu, User user) {
        this(id, menu, user, LocalDate.now());
    }

    public Vote(Integer id, Menu menu, User user, LocalDate voteDate) {
        super(id);
        this.voteDate = voteDate;
        this.menu = menu;
        this.user = user;
    }
}
