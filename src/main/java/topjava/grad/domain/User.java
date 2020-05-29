package topjava.grad.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String email;
    private String password;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Vote> votes;
}
