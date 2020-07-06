package topjava.grad.domain.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import topjava.grad.domain.HasId;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MenuTo implements HasId {
    private Integer id;
    private final Integer restaurantId;
    private final LocalDate date;
}
