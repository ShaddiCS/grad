package topjava.grad.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import topjava.grad.domain.to.DishTo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dish")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Dish extends AbstractBaseEntity {
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    @SafeHtml
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 500000)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    @JsonBackReference
    private Menu menu;

    public Dish(Integer id, String name, Integer price) {
        this(id, name, price, null);
    }

    public Dish(Integer id, String name, Integer price, Menu menu) {
        super(id);
        this.name = name;
        this.price = price;
        this.menu = menu;
    }

    public Dish(DishTo dishTo) {
        this(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
    }
}
