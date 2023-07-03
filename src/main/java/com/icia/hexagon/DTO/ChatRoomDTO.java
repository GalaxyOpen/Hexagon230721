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

    public void handleAction(WebSocketSession session, ChatMessageDTO message, ChatService service){
    // message 에 담긴 타입을 확인한다.
    // 이 때 message 에서 getType 으로 가져온 내용이
    // ChatMessageDTO 의 열거형인 MessageType 안에 있는 ENTER 와 동일한 값이라면
    if(message.getType().equals(ChatMessageDTO.MessageType.ENTER)){
        //session 에 넘어온 session 을 담고,
        sessions.add(session);

        //message 에는 입장하였다는 메시지를 띄운다.
        message.setMessage(message.getSender()+"님이 입장하셨습니다.");
        sendMessage(message, service);
    }else if(message.getType().equals(ChatMessageDTO.MessageType.TALK)){
            message.setMessage(message.getMessage());
            sendMessage(message, service);
        }
    }

    public <T> void sendMessage(T message, ChatService service){

        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
    }
//    private Long id;
//    private String roomName;
//    private String roomReceiver;
//    private Long roomId;
}
