package method.optimisation.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by Danil Lyskin at 16:51 28.02.2021
 */

@Slf4j
@ControllerAdvice
public class ApiInputHandler {

    @ExceptionHandler(value = {ApiInputException.class})
    public ResponseEntity<Object> handleNonexistentLoginAttempt(ApiInputException e) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        log.error("Incorrect input: " + e.getMessage());
        return new ResponseEntity<>(apiException, badRequest);
    }
}
