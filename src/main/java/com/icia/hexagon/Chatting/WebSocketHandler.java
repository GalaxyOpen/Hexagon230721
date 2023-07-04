package com.icia.hexagon.Chatting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.hexagon.DTO.ChatMessageDTO;
import com.icia.hexagon.DTO.ChatRoomDTO;
import com.icia.hexagon.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("{}", payload);

        ChatMessageDTO chatMessageDTO = objectMapper.readValue(payload, ChatMessageDTO.class);
        ChatRoomDTO chatRoomDTO = chatService.findRoomById(chatMessageDTO.getRoomId());
        chatRoomDTO.handleAction(session, chatMessageDTO, chatService);
    }
}
