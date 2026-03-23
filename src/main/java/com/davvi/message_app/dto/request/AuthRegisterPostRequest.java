package com.davvi.message_app.dto.request;

public record AuthRegisterPostRequest(
        String username,
        String password
) {
}
