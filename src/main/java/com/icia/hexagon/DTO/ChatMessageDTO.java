package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long roomId;
    private String chatWriter;
    private String chatContents;
    private String createdAt;
}
