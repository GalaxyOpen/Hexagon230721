package com.icia.hexagon.Chatting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;


@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

//    @EventListener
//    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if (username != null) {
//            logger.info("User Disconnected : " + username);
//
//            ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
//            chatMessageDTO.setType(ChatMessageDTO.MessageType.LEAVE);
//            chatMessageDTO.setSender(username);
//
//            messagingTemplate.convertAndSend("/topic/public", chatMessageDTO);
//        }
//    }
}