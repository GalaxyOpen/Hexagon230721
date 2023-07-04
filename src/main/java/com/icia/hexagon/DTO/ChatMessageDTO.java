package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    public enum MessageType{
        ENTER, TALK
    }
    private MessageType type;
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 채팅 메시지
    private String createdAt; // 채팅 발송 시간
}
