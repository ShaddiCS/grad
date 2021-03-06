package topjava.grad.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import topjava.grad.util.ValidationUtil;
import topjava.grad.util.exception.ErrorInfo;
import topjava.grad.util.exception.ErrorType;
import topjava.grad.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static topjava.grad.util.ValidationUtil.getMessage;
import static topjava.grad.util.exception.ErrorType.DATA_ERROR;
import static topjava.grad.util.exception.ErrorType.VALIDATION_ERROR;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class RestExceptionHandler {

    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DUPLICATE_VOTE = "This user already voted today";

    public static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    private static final Map<String, String> CONSTRAINS_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "user_unique_date_idx", EXCEPTION_DUPLICATE_VOTE);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> applicationError(HttpServletRequest req, NotFoundException e) {
        ErrorInfo errorInfo = new ErrorInfo(req.getRequestURL(), e.getErrorType(), e.getErrorType().getError());
        return ResponseEntity.status(e.getErrorType().getStatus()).body(errorInfo);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e){
        String rootMessage = ValidationUtil.getRootCause(e).getMessage();
        if (rootMessage != null) {
            String lowerCaseMessage = rootMessage.toLowerCase();
            for(Map.Entry<String, String> entry : CONSTRAINS_MAP.entrySet()) {
                if (lowerCaseMessage.contains(entry.getKey())) {
                    return logAndGetErrorInfo(req.getRequestURL(), false, VALIDATION_ERROR, e);
                }
            }
        }
        return logAndGetErrorInfo(req.getRequestURL(), true, VALIDATION_ERROR, e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo bindValidationError(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        String[] details = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);

        return logAndGetErrorInfo(req.getRequestURI(), false, VALIDATION_ERROR, e, details);
    }

    public ErrorInfo logAndGetErrorInfo(CharSequence requestUrl, boolean logException, ErrorType errorType, Exception e, String... details) {
        Throwable cause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error("{} at request {}: {}", errorType, requestUrl, cause);
        } else {
            log.warn("{} at request {}: {}", errorType, requestUrl, cause.toString());
        }

        return new ErrorInfo(requestUrl, VALIDATION_ERROR, VALIDATION_ERROR.getError(), details);
    }
}
