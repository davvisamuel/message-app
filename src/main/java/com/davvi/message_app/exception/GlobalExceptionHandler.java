package com.davvi.message_app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<DefaultErrorMessage> handleUsernameAlreadyExists(UsernameAlreadyExists e) {
        var defaultErrorMessage = new DefaultErrorMessage(e.getStatusCode().value(), e.getMessage());

        return ResponseEntity.badRequest().body(defaultErrorMessage);
    }
}
