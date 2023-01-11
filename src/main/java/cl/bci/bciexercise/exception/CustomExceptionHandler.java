package cl.bci.bciexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomException> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomException.builder()
                    .mensaje(e.getMessage()).build());
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<CustomException> handleWeakPasswordException(WeakPasswordException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(CustomException.builder()
                        .mensaje(e.getMensaje()).build());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CustomException> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CustomException.builder()
                        .mensaje(e.getMensaje()).build());
    }

}
