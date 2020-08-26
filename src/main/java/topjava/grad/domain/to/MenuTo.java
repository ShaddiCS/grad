package topjava.grad.domain.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import topjava.grad.domain.HasId;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.time.LocalDate;

@Data
public class MenuTo implements HasId {
    private Integer id;
    @NotNull
    private final Integer restaurantId;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @ConstructorProperties({"id", "restaurantId", "date"})
    public MenuTo(Integer id, @NotNull Integer restaurantId, @NotNull LocalDate date) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.date = date;
    }
}
