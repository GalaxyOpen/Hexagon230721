package com.icia.hexagon.DTO;

import lombok.*;

import java.awt.*;
@Getter
@Setter
public class ChatMessageDTO {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT, // Talk
        JOIN, //enter
        LEAVE
    }
}