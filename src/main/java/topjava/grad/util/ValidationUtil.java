package topjava.grad.util;

import topjava.grad.domain.HasId;
import topjava.grad.util.exception.IllegalRequestDataException;

public class ValidationUtil {

    private ValidationUtil(){}

    public static void assureIdConsistent(HasId bean, HasId beanFromDb) {
        assureIdConsistent(bean, beanFromDb.getId());
    }

    public static void assureIdConsistent(HasId bean, Integer id) {
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

    // http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable e) {
            Throwable cause;
            Throwable result = e;

            while(null != (cause = result.getCause())  && (result != cause) ) {
                result = cause;
            }
            return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }
}
