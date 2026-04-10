package com.davvi.message_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FriendRequestAlreadyExistsException extends ResponseStatusException {

    public FriendRequestAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
