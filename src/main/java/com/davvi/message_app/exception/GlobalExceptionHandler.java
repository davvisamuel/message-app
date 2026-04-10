package com.davvi.message_app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<DefaultErrorMessage> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        var defaultErrorMessage = new DefaultErrorMessage(e.getStatusCode().value(), e.getMessage());

        return ResponseEntity.badRequest().body(defaultErrorMessage);
    }

    @ExceptionHandler(FriendRequestAlreadyExistsException.class)
    public ResponseEntity<DefaultErrorMessage> handleFriendRequestAlreadyExists(FriendRequestAlreadyExistsException e) {
        var defaultErrorMessage = new DefaultErrorMessage(e.getStatusCode().value(), e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(defaultErrorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleUserNotFound(UserNotFoundException e) {
        var defaultErrorMessage = new DefaultErrorMessage(e.getStatusCode().value(), e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(defaultErrorMessage);
    }
}
