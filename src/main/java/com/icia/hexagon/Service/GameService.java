package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.GameFileEntity;
import com.icia.hexagon.Repository.GameFileRepository;
import com.icia.hexagon.Repository.GameRepository;
import com.icia.hexagon.Util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameFileRepository gameFileRepository;

    public Long save(GameDTO gameDTO) throws IOException {
        if(gameDTO.getGameFile()==null || gameDTO.getGameFile().get(0).isEmpty()){
            //파일 없는 경우
            GameEntity gameEntity = GameEntity.toSaveEntity(gameDTO);
            return gameRepository.save(gameEntity).getId();
        }else{
            GameEntity fileEntity = GameEntity.toSaveEntityWithFile(gameDTO);
            GameEntity dataEntity = gameRepository.save(fileEntity);

            for(MultipartFile gameFile : gameDTO.getGameFile()){
                String originalFileName = gameFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis()+"_"+originalFileName;
                String savePath = "D:\\Hexagon\\"+storedFileName;
                gameFile.transferTo(new File(savePath));

                GameFileEntity gameFileEntity =
                        GameFileEntity.toSaveGameFileEntity(dataEntity, originalFileName, storedFileName);
                gameFileRepository.save(gameFileEntity);
            }
            return dataEntity.getId();
        }
    }
    public Page<GameDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber()-1;
        int pageLimit = 3;

        Page<GameEntity> gameEntities = null;
        if(type.equals("title")){
            gameEntities = gameRepository.findByGameTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }else if(type.equals("distr")){
            gameEntities = gameRepository.findByGameDistrContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
            // 배급사로 검색
        }else if(type.equals("creater")){
            gameEntities = gameRepository.findByGameCreatorContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
            // 제작사로 검색
        }else{
            gameEntities = gameRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        }
        Page<GameDTO> gameDTOS = gameEntities.map(gameEntity -> GameDTO.builder()
                .id(gameEntity.getId())
                .gameTitle(gameEntity.getGameTitle())
                .gameGenre(gameEntity.getGameGenre())
                .gameCreator(gameEntity.getGameCreator())
                .gameGrade(gameEntity.getGameGrade())
                .createdAt(UtilClass.dateFormat(gameEntity.getCreatedAt()))
                .salesPrice(gameEntity.getSalesPrice())
                .build());
        return gameDTOS;
    }

}
