package topjava.grad.domain.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import topjava.grad.domain.HasId;

@Data
@AllArgsConstructor
public class DishTo implements HasId {
    private Integer id;
    private final String name;
    private final Integer price;
    private final Integer menuId;
}
