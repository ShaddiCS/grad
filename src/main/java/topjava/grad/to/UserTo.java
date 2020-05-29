package topjava.grad.to;

import lombok.Data;

@Data
public class UserTo {
    private final String email;
    private final String password;
    private final boolean canVote;
}
