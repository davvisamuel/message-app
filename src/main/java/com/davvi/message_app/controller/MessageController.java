package com.davvi.message_app.controller;

import com.davvi.message_app.dto.request.MessageRequest;
import com.davvi.message_app.dto.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MessageController {

    @MessageMapping("/message.send/{room}")
    @SendTo("/topic/{room}")
    public MessageResponse sendMessage(@DestinationVariable String room, MessageRequest messageRequest) {
        log.debug("Mensagem enviada para sala: {}", room);
        return new MessageResponse(messageRequest.username(), messageRequest.message());
    }
}
