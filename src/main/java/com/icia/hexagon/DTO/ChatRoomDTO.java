package com.icia.hexagon.DTO;

import com.icia.hexagon.Service.ChatService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data

public class ChatRoomDTO {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomDTO(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatMessageDTO chatMessageDTO, ChatService service){
    // message 에 담긴 타입을 확인한다.
    // 이 때 message 에서 getType 으로 가져온 내용이
    // ChatMessageDTO 의 열거형인 MessageType 안에 있는 ENTER 와 동일한 값이라면
    if(chatMessageDTO.getType().equals(ChatMessageDTO.MessageType.JOIN)){
        //session 에 넘어온 session 을 담고,
        sessions.add(session);

        //message 에는 입장하였다는 메시지를 띄운다.
        chatMessageDTO.setContent(chatMessageDTO.getSender()+"님이 입장하셨습니다.");
        sendMessage(chatMessageDTO, service);
    }else if(chatMessageDTO.getType().equals(ChatMessageDTO.MessageType.CHAT)){
        chatMessageDTO.setContent(chatMessageDTO.getContent());
            sendMessage(chatMessageDTO, service);
        }
    }

    public <T> void sendMessage(T message, ChatService service){

        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
    }

}
