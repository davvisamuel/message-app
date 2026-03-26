package com.davvi.message_app.controller;

import com.davvi.message_app.dto.request.MessageRequest;
import com.davvi.message_app.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/private-message")
    public void sendPrivateMessage(MessageRequest messageRequest, Principal principal) {
        log.info("Request received for '{}'", messageRequest);
        messageService.sendPrivateMessage(messageRequest.username(), messageRequest.message(), principal);
    }
}
