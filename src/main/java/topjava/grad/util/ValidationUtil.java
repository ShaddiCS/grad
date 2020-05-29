package topjava.grad.util;

import topjava.grad.domain.HasId;
import topjava.grad.util.exception.IllegalRequestDataException;
import topjava.grad.util.exception.NotFoundException;

public class ValidationUtil {

    public static void assureIdConsistent(HasId bean, Long id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (!bean.getId().equals(id)) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static <T> T checkNotFound(T object, long id) {
        checkNotFound(object != null, id);
        return object;
    }

    public static void checkNotFound(boolean found, long id) {
        if (!found) {
            throw new NotFoundException("id=" + id);
        }
    }
}
