package com.davvi.message_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(String username, String message) {
        var destination = "/queue/" + username;
        simpMessagingTemplate.convertAndSendToUser(username, destination, message);
    }
}
