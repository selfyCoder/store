package kz.store.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalApplicationExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Object> handleUserException(ClientException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
