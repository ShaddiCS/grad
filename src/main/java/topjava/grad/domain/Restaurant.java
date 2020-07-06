package topjava.grad.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Restaurant extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
