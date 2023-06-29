package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GameReviewDTO {
    private Long id;
    private String reviewWriter;
    private String reviewContents;
    private Long memberId;
    private Long gameId;
}
