package topjava.grad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Vote extends AbstractBaseEntity{

    private LocalDate voteDate;
    private LocalTime voteTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private Menu menu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote(Menu menu, User user) {
        this.voteTime = LocalTime.now();
        this.voteDate = LocalDate.now();
        this.menu = menu;
        this.user = user;
    }
}
