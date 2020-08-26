package topjava.grad.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private ErrorType errorType;

    public NotFoundException(String message) {
        super(message);
        this.errorType = ErrorType.DATA_NOT_FOUND;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
