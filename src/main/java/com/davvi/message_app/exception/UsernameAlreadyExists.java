package com.davvi.message_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyExists extends ResponseStatusException {

    public UsernameAlreadyExists(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
