package topjava.grad.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    DATA_ERROR("Data error", HttpStatus.CONFLICT),
    DATA_NOT_FOUND("Data not found", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR("Validation error", HttpStatus.UNPROCESSABLE_ENTITY);

    private final String error;
    private final HttpStatus status;

    ErrorType(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
