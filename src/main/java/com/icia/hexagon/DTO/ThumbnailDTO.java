package com.icia.hexagon.DTO;


import com.icia.hexagon.Entity.ThumbnailEntity;
import lombok.Data;

@Data
public class ThumbnailDTO {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private Long gameId;

    public static ThumbnailDTO toDTO(ThumbnailEntity entity) {
        ThumbnailDTO thumbnailDTO = new ThumbnailDTO();
        thumbnailDTO.setId(entity.getId());
        thumbnailDTO.setGameId(entity.getGameEntity().getId());
        thumbnailDTO.setOriginalFileName(entity.getOriginalFileName());
        thumbnailDTO.setStoredFileName(entity.getStoredFileName());
        return thumbnailDTO;
    }
}
