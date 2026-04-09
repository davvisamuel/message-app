package com.davvi.message_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FriendRequestAlreadyExists extends ResponseStatusException {

    public FriendRequestAlreadyExists(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
