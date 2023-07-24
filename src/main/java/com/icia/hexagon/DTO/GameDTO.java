package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.GameFileEntity;
import com.icia.hexagon.Entity.ThumbnailEntity;
import com.icia.hexagon.Util.UtilClass;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "thumbnail")
public class GameDTO {

    private Long id;
    private String gameTitle;
    private String gameGenre;
    private String gameCreator;
    private String gameDistr;
    private String gameGrade;
    private String gameIntro;
    private Long releasePrice;
    private int discountRate;
    private Long salesPrice;
    private String createdAt;
    private MultipartFile thumbnail;
    private String thumbnailUrl; // 추가한 썸네일 파일의 URL 필드
    private List<MultipartFile> gameFile;
    private List<String> originalFileName;
    private List<String> storedFileName;
    private int fileAttached;
    private Long memberId;
    private String youtubeUrl;

    public static GameDTO toDTO(GameEntity gameEntity) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(gameEntity.getId());
        gameDTO.setGameTitle(gameEntity.getGameTitle());
        gameDTO.setGameGenre(gameEntity.getGameGenre());
        gameDTO.setGameCreator(gameEntity.getGameCreator());
        gameDTO.setGameDistr(gameEntity.getGameDistr());
        gameDTO.setGameGrade(gameEntity.getGameGrade());
        gameDTO.setGameIntro(gameEntity.getGameIntro());
        gameDTO.setReleasePrice(gameEntity.getReleasePrice());
        gameDTO.setCreatedAt(UtilClass.dateFormat(gameEntity.getCreatedAt()));
        gameDTO.setDiscountRate(gameEntity.getDiscountRate());
        gameDTO.setSalesPrice(gameEntity.getSalesPrice());
        gameDTO.setYoutubeUrl(gameEntity.getYoutubeUrl());

        // 썸네일 엔티티 리스트를 가져옵니다.
        List<ThumbnailEntity> thumbnailEntities = gameEntity.getThumbnailEntities();
        if (thumbnailEntities != null && !thumbnailEntities.isEmpty()) {
            // 첫 번째 썸네일 엔티티의 storedFileName 또는 파일 URL을 가져와서 gameDTO의 thumbnailUrl에 설정합니다.
            gameDTO.setThumbnailUrl(thumbnailEntities.get(0).getStoredFileName());
        }

        // 게임 멤버의 ID를 gameDTO에 설정합니다.
        gameDTO.setMemberId(gameEntity.getMemberEntity().getId());

        // gameFileEntityList를 초기화하여 LazyInitializationException 방지
        if (gameEntity.getGameFileEntityList() != null) {
            gameEntity.getGameFileEntityList().size();
        }

        // 파일 첨부 여부에 따라 파일 이름 리스트를 설정합니다.
        if (gameEntity.getFileAttached() == 1) {
            gameDTO.setFileAttached(1);
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            for (GameFileEntity gameFileEntity : gameEntity.getGameFileEntityList()) {
                originalFileNameList.add(gameFileEntity.getOriginalFileName());
                storedFileNameList.add(gameFileEntity.getStoredFileName());
            }
            gameDTO.setOriginalFileName(originalFileNameList);
            gameDTO.setStoredFileName(storedFileNameList);
        } else {
            gameDTO.setFileAttached(0);
        }

        return gameDTO;
    }



}
