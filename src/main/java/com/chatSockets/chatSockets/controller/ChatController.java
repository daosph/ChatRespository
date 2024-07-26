package com.chatSockets.chatSockets.controller;

import com.chatSockets.chatSockets.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
@Slf4j
public class ChatController {
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chatMessage(@DestinationVariable String roomId, ChatMessage message){
        log.info("Message received: {}", message);
        log.info("Message received: {}", roomId);
        return new ChatMessage(message.getMessage(), message.getUser());
    }
}

