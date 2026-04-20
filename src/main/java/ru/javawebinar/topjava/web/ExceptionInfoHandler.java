package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.exception.ErrorType.APP_ERROR;
import static ru.javawebinar.topjava.util.exception.ErrorType.DATA_ERROR;
import static ru.javawebinar.topjava.util.exception.ErrorType.DATA_NOT_FOUND;
import static ru.javawebinar.topjava.util.exception.ErrorType.VALIDATION_ERROR;

import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private final MessageSourceAccessor messageSourceAccessor;

    public ExceptionInfoHandler(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    private static final String EXCEPTION_DUPLICATE_EMAIL = "user.duplicate.email";

    private static final String EXCEPTION_DUPLICATE_DATETIME = "meal.duplicate.datetime";

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "meal_unique_user_datetime_idx", EXCEPTION_DUPLICATE_DATETIME);


    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo notFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINS_I18N_MAP.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    String message = messageSourceAccessor.getMessage(entry.getValue());
                    return logAndGetErrorInfo(req, message, false, VALIDATION_ERROR);
                }
            }
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo validationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public ErrorInfo bindException(HttpServletRequest req, BindException e) {
        String customMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("[%s]: %s", error.getField(), messageSourceAccessor.getMessage(error)))
                .collect(Collectors.joining("\n"));
        return logAndGetErrorInfo(req, customMessage, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TransactionSystemException.class)
    public ErrorInfo handleTransactionSystemException(HttpServletRequest req, TransactionSystemException e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo internalError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        return logAndGetErrorInfo(req, rootCause.getMessage(), logException, errorType);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, String message, boolean logException, ErrorType errorType) {
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), message);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), message);
        }
        return new ErrorInfo(req.getRequestURL(), errorType, message);
    }
}