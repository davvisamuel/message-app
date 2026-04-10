package com.davvi.message_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FriendRequestNotFoundException extends ResponseStatusException {

    public FriendRequestNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
