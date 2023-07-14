package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.GameFileEntity;
import com.icia.hexagon.Util.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
        gameDTO.setThumbnail(gameDTO.getThumbnail());

        if(gameEntity.getFileAttached()==1){
            gameDTO.setFileAttached(1);
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            for(GameFileEntity gameFileEntity: gameEntity.getGameFileEntityList()){
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
