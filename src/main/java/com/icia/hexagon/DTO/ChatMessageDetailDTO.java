package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.ChatMessageEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDetailDTO {
    private Long id;
    private Long chatRoomId;
    private String roomId;
    private String writer;
    private String message;

    private LocalDateTime sendDate;

    public static ChatMessageDetailDTO toChatMessageDetailDTO(ChatMessageEntity chatMessageEntity){
        ChatMessageDetailDTO chatMessageDetailDTO = new ChatMessageDetailDTO();

        chatMessageDetailDTO.setId(chatMessageEntity.getId());
        chatMessageDetailDTO.setChatRoomId(chatMessageEntity.getChatRoomEntity().getId());
        chatMessageDetailDTO.setRoomId(chatMessageEntity.getChatRoomEntity().getRoomId());
        chatMessageDetailDTO.setWriter(chatMessageEntity.getWriter());
        chatMessageDetailDTO.setMessage(chatMessageEntity.getMessage());
        chatMessageDetailDTO.setSendDate(chatMessageEntity.getSendDate());

        return chatMessageDetailDTO;
    }
}