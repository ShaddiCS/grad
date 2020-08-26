package topjava.grad.domain.to;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import topjava.grad.domain.HasId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

@Data
public class DishTo implements HasId {
    private Integer id;
    @NotBlank
    @Size(min = 2, max = 100)
    private final String name;
    @NotNull
    @Range(min = 10, max = 500000)
    private final Integer price;
    @NotNull
    private final Integer menuId;

    @ConstructorProperties({"id", "name", "price", "menuId"})
    public DishTo(Integer id, String name, Integer price, Integer menuId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuId = menuId;
    }
}
