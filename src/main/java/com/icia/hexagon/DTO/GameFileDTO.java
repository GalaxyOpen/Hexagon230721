package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.GameFileEntity;
import com.icia.hexagon.Entity.ThumbnailEntity;
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

    public static GameFileDTO toDTO(GameFileEntity entity) {
        GameFileDTO fileDTO = new GameFileDTO();
        fileDTO.setId(entity.getId());
        fileDTO.setGameId(entity.getGameEntity().getId());
        fileDTO.setOriginalFileName(entity.getOriginalFileName());
        fileDTO.setStoredFileName(entity.getStoredFileName());
        return fileDTO;
    }
}
