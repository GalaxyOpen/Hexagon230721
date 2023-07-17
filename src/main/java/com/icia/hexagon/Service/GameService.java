package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.GameFileDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.ThumbnailDTO;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.GameFileEntity;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Entity.ThumbnailEntity;
import com.icia.hexagon.Repository.GameFileRepository;
import com.icia.hexagon.Repository.GameRepository;
import com.icia.hexagon.Repository.ThumbnailRepository;
import com.icia.hexagon.Util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameFileRepository gameFileRepository;
    private final ThumbnailRepository thumbnailRepository;

    @Transactional
    public Long save(GameDTO gameDTO, MemberDTO memberDTO) throws IOException {
        GameEntity gameEntity;
        if (gameDTO.getGameFile() == null || gameDTO.getGameFile().get(0).isEmpty()) {
            // 파일이 없는 경우
            gameEntity = GameEntity.toSaveEntity(gameDTO, MemberEntity.toUpdateEntity(memberDTO));
        } else {
            // 게임 파일이 있는 경우
            gameEntity = GameEntity.toSaveEntityWithFile(gameDTO, MemberEntity.toUpdateEntity(memberDTO));
        }

        if (gameDTO.getThumbnail() != null && !gameDTO.getThumbnail().isEmpty()) {
            // 썸네일 저장 로직
            MultipartFile thumbnailFile = gameDTO.getThumbnail();
            String thumbnailOriginalFileName = thumbnailFile.getOriginalFilename();
            String thumbnailStoredFileName = System.currentTimeMillis() + "_" + thumbnailOriginalFileName;
            String thumbnailSavePath = "D:\\Hexagon\\" + thumbnailStoredFileName;
            thumbnailFile.transferTo(new File(thumbnailSavePath));

            ThumbnailEntity thumbnailEntity = new ThumbnailEntity();
            thumbnailEntity.setOriginalFileName(thumbnailOriginalFileName);
            thumbnailEntity.setStoredFileName(thumbnailStoredFileName);

            gameEntity.getThumbnailEntities().add(thumbnailEntity);

            // thumbnailEntity에 gameEntity 설정
            thumbnailEntity.setGameEntity(gameEntity);
            thumbnailRepository.save(thumbnailEntity);
        }

        if (gameEntity.getFileAttached() == 1) {
            // 게임 파일 저장 로직
            for (MultipartFile gameFile : gameDTO.getGameFile()) {
                String originalFileName = gameFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "D:\\Hexagon\\" + storedFileName;
                gameFile.transferTo(new File(savePath));

                GameFileEntity gameFileEntity = GameFileEntity.toSaveGameFileEntity(gameEntity, originalFileName, storedFileName);
                gameFileRepository.save(gameFileEntity);
            }
        }

        return gameRepository.save(gameEntity).getId();
    }

    public Page<GameDTO> paging(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber()-1;
        int pageLimit = 20;


        Page<GameEntity> gameEntities = null;
        if (type.equals("title")) {
            gameEntities = gameRepository.findByGameTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else if (type.equals("distr")) {
            gameEntities = gameRepository.findByGameDistrContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            // 배급사로 검색
        } else if (type.equals("creater")) {
            gameEntities = gameRepository.findByGameCreatorContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            // 제작사로 검색
        } else {
            gameEntities = gameRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        }
        Page<GameDTO> gameDTOS = gameEntities.map(gameEntity -> GameDTO.builder()
                .id(gameEntity.getId())
                .gameTitle(gameEntity.getGameTitle())
                .gameGenre(gameEntity.getGameGenre())
                .gameCreator(gameEntity.getGameCreator())
                .gameGrade(gameEntity.getGameGrade())
                .createdAt(UtilClass.dateFormat(gameEntity.getCreatedAt()))
                .releasePrice(gameEntity.getReleasePrice())
                .discountRate(gameEntity.getDiscountRate())
                .salesPrice(gameEntity.getSalesPrice())
                .build());

        return gameDTOS;
    }

    public Page<GameDTO> release(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = pageable.getPageSize();

        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        Page<GameEntity> gameEntities = null;
        if (type.equals("title")) {
            gameEntities = gameRepository.findByGameTitleContainingAndCreatedAtAfter(q, oneDayAgo, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else if (type.equals("distr")) {
            gameEntities = gameRepository.findByGameDistrContainingAndCreatedAtAfter(q, oneDayAgo, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else if (type.equals("creater")) {
            gameEntities = gameRepository.findByGameCreatorContainingAndCreatedAtAfter(q, oneDayAgo, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            gameEntities = gameRepository.findAllByCreatedAtAfter(oneDayAgo, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }

        Page<GameDTO> gameDTOS = gameEntities.map(gameEntity -> GameDTO.builder()
                .id(gameEntity.getId())
                .gameTitle(gameEntity.getGameTitle())
                .gameGenre(gameEntity.getGameGenre())
                .gameCreator(gameEntity.getGameCreator())
                .gameGrade(gameEntity.getGameGrade())
                .createdAt(UtilClass.dateFormat(gameEntity.getCreatedAt()))
                .releasePrice(gameEntity.getReleasePrice())
                .discountRate(gameEntity.getDiscountRate())
                .salesPrice(gameEntity.getSalesPrice())
                .build());

        return gameDTOS;
    }

    public Page<GameDTO> discount(Pageable pageable, String type, String q) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = pageable.getPageSize();

        Page<GameEntity> gameEntities = null;
        if (type.equals("title")) {
            gameEntities = gameRepository.findByGameTitleContainingAndDiscountRateGreaterThanOrderByDiscountRateDesc(q, 0, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "discountRate")));
        } else if (type.equals("distr")) {
            gameEntities = gameRepository.findByGameDistrContainingAndDiscountRateGreaterThanOrderByDiscountRateDesc(q, 0, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "discountRate")));
        } else if (type.equals("creater")) {
            gameEntities = gameRepository.findByGameCreatorContainingAndDiscountRateGreaterThanOrderByDiscountRateDesc(q, 0, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "discountRate")));
        } else {
            gameEntities = gameRepository.findByDiscountRateGreaterThanOrderByDiscountRateDesc(0, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "discountRate")));
        }

        Page<GameDTO> gameDTOS = gameEntities.map(gameEntity -> {
            GameDTO gameDTO = GameDTO.builder()
                    .id(gameEntity.getId())
                    .gameTitle(gameEntity.getGameTitle())
                    .gameGenre(gameEntity.getGameGenre())
                    .gameCreator(gameEntity.getGameCreator())
                    .gameGrade(gameEntity.getGameGrade())
                    .createdAt(UtilClass.dateFormat(gameEntity.getCreatedAt()))
                    .releasePrice(gameEntity.getReleasePrice())
                    .discountRate(gameEntity.getDiscountRate())
                    .salesPrice(gameEntity.getSalesPrice())
                    .build();

            // 할인률 값을 가져와서 설정
            gameDTO.setDiscountRate(gameEntity.getDiscountRate());

            return gameDTO;
        });

        return gameDTOS;
    }

    public GameDTO findById(Long id) {
        GameEntity gameEntity = gameRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return GameDTO.toDTO(gameEntity);
    }

    @Transactional
    public Long update(GameDTO gameDTO, MemberDTO memberDTO) throws IOException {
        GameEntity gameEntity = gameRepository.findById(gameDTO.getId()).orElse(null);
        if (gameEntity == null) {
            return null;
        }

        // 파일 업로드 수행
        List<GameFileEntity> newGameFiles = new ArrayList<>();

        if (gameDTO.getGameFile() != null && !gameDTO.getGameFile().get(0).isEmpty()) {
            // 기존 파일 목록을 삭제합니다.
            for (GameFileEntity gameFileEntity : gameEntity.getGameFileEntityList()) {
                deleteGameFile(gameFileEntity);
            }

            // 새로운 게임 파일 목록을 생성합니다.
            for (MultipartFile gameFile : gameDTO.getGameFile()) {
                String originalFileName = gameFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "D:\\Hexagon\\" + storedFileName;
                gameFile.transferTo(new File(savePath));

                GameFileEntity gameFileEntity = GameFileEntity.toSaveGameFileEntity(gameEntity, originalFileName, storedFileName);
                gameFileRepository.save(gameFileEntity);
                newGameFiles.add(gameFileEntity);
            }
        } else {
            // 파일을 등록하지 않은 경우, 기존 파일 목록을 유지합니다.
            newGameFiles.addAll(gameEntity.getGameFileEntityList());
        }

        // 기존 파일 목록을 새로운 파일 목록으로 갱신합니다.
        gameEntity.getGameFileEntityList().clear();
        gameEntity.getGameFileEntityList().addAll(newGameFiles);

        // 썸네일 업로드 수행
        ThumbnailEntity newThumbnailEntity = null;
        if (gameDTO.getThumbnail() != null && !gameDTO.getThumbnail().isEmpty()) {
            MultipartFile thumbnailFile = gameDTO.getThumbnail();
            String thumbnailOriginalFileName = thumbnailFile.getOriginalFilename();
            String thumbnailStoredFileName = System.currentTimeMillis() + "_" + thumbnailOriginalFileName;
            String thumbnailSavePath = "D:\\Hexagon\\" + thumbnailStoredFileName;
            thumbnailFile.transferTo(new File(thumbnailSavePath));

            newThumbnailEntity = new ThumbnailEntity();
            newThumbnailEntity.setOriginalFileName(thumbnailOriginalFileName);
            newThumbnailEntity.setStoredFileName(thumbnailStoredFileName);

            // 기존의 썸네일 엔티티를 삭제합니다.
            ThumbnailEntity existingThumbnail = gameEntity.getThumbnailEntities().stream().findFirst().orElse(null);
            if (existingThumbnail != null) {
                deleteThumbnail(existingThumbnail);
            }

            // 새로운 썸네일 엔티티를 저장합니다.
            gameEntity.getThumbnailEntities().clear();
            gameEntity.getThumbnailEntities().add(newThumbnailEntity);
            newThumbnailEntity.setGameEntity(gameEntity);
            thumbnailRepository.save(newThumbnailEntity);
        }

        // 필드 값 업데이트
        GameEntity updatedGameEntity = GameEntity.toUpdateWithFileEntity(gameDTO, MemberEntity.toUpdateEntity(memberDTO));
        gameRepository.save(updatedGameEntity);

        return updatedGameEntity.getId();
    }

    private void deleteGameFile(GameFileEntity gameFileEntity) {
        // 기존 파일을 삭제하는 로직을 구현합니다.
        gameFileRepository.delete(gameFileEntity);
    }

    private void deleteThumbnail(ThumbnailEntity thumbnailEntity) {
        // 기존 썸네일을 삭제하는 로직을 구현합니다.
        thumbnailRepository.delete(thumbnailEntity);
    }


    @Transactional
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }


    public ThumbnailDTO GameThumbnails(Long gameId) {
        Optional<ThumbnailEntity> gameEntity = thumbnailRepository.findByGameEntity_Id(gameId);
        return ThumbnailDTO.toDTO(gameEntity.get());
    }

    public GameFileDTO fileByGameId(Long id) {
        GameFileEntity gameFiles = gameFileRepository.findByGameEntity_Id(id).get();
        GameFileDTO gameFileDTO = GameFileDTO.toDTO(gameFiles);
        return gameFileDTO;
    }
}
