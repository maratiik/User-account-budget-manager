package maratik.budget_manager.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppError> handleEntityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(
                new AppError(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppError> handleIllegalArgument(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new AppError(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public record AppError(
            int statusCode,
            String message
    ) {}
}
