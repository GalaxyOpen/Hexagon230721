package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.GameReviewEntity;
import com.icia.hexagon.Util.UtilClass;
import lombok.Data;

@Data
public class GameReviewDTO {
    private Long id;
    private String reviewWriter;
    private String reviewContents;
    private Long memberId;
    private Long gameId;
    private String createdAt;
    private String updateAt;

    public static GameReviewDTO toDTO(GameReviewEntity gameReviewEntity) {
        GameReviewDTO gameReviewDTO = new GameReviewDTO();
        gameReviewDTO.setId(gameReviewEntity.getId());
        gameReviewDTO.setReviewWriter(gameReviewEntity.getReviewWriter());
        gameReviewDTO.setReviewContents(gameReviewEntity.getReviewContents());
        gameReviewDTO.setMemberId(gameReviewEntity.getMemberEntity().getId());
        gameReviewDTO.setGameId(gameReviewEntity.getGameEntity().getId());
        gameReviewDTO.setCreatedAt(UtilClass.dateFormat(gameReviewEntity.getCreatedAt()));
        gameReviewDTO.setUpdateAt(UtilClass.dateFormat(gameReviewEntity.getUpdatedAt()));
        return gameReviewDTO;
    }
}
