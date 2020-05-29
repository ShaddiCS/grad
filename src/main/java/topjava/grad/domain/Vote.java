package topjava.grad.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Vote implements HasId{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate voteDate;
    private LocalTime voteTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote(Restaurant restaurant, User user) {
        this.voteTime = LocalTime.now();
        this.voteDate = LocalDate.now();
        this.restaurant = restaurant;
        this.user = user;
    }
}
