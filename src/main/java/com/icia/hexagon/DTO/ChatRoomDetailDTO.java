package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.ChatRoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailDTO {
    private Long id;
    private String roomId;
    private String roomName;

    public static ChatRoomDetailDTO toChatRoomDetailDTO(ChatRoomEntity chatRoomEntity){
        ChatRoomDetailDTO chatRoomDetailDTO = new ChatRoomDetailDTO();

        chatRoomDetailDTO.setId(chatRoomEntity.getId());
        chatRoomDetailDTO.setRoomId(chatRoomEntity.getRoomId());
        chatRoomDetailDTO.setRoomName(chatRoomEntity.getRoomName());

        return chatRoomDetailDTO;
    }
}
