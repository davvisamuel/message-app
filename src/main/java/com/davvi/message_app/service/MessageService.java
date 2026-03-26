package com.davvi.message_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendPrivateMessage(String username, String message, Principal principal) {
        log.info("Send message to: {}", username);

        var sender = principal.getName();

        simpMessagingTemplate.convertAndSendToUser(username, "/queue/private-message", message);

        if (!sender.equals(username)) {
            simpMessagingTemplate.convertAndSendToUser(sender, "/queue/private-message", message);
        }
    }
}
