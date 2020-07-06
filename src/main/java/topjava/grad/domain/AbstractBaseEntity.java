package topjava.grad.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity implements HasId{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
