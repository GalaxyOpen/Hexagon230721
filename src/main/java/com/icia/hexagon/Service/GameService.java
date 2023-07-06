package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.GameFileEntity;
import com.icia.hexagon.Repository.GameFileRepository;
import com.icia.hexagon.Repository.GameRepository;
import lombok.RequiredArgsConstructor;
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

}
