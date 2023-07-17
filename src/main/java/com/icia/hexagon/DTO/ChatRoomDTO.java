package com.icia.hexagon.DTO;

import com.icia.hexagon.Service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private String roomId;
    private String roomName;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDTO create(String roomName) {
        ChatRoomDTO room = new ChatRoomDTO();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = roomName;
        return room;
    }
}
