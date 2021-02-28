package method.optimisation.exceptions.method_types;

import lombok.extern.slf4j.Slf4j;
import method.optimisation.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class ApiMethodTypeHandler {

    @ExceptionHandler(value = {ApiMethodTypeException.class})
    public ResponseEntity<Object> handleNonexistentLoginAttempt(ApiMethodTypeException e) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        log.error("Problem with type of method: " + e.getMessage());
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = {ApiMethodNotFoundException.class})
    public ResponseEntity<Object> handleNonexistentLoginAttempt(ApiMethodNotFoundException e) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        log.error("Method not found for solve: " + e.getMessage());
        return new ResponseEntity<>(apiException, notFound);
    }
}
