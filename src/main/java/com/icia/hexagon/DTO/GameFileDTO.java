package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GameFileDTO {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private Long gameId;

}
