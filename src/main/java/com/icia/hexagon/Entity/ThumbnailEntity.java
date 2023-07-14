package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "game_thumbnail_table")
public class ThumbnailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String originalFileName;

    @Column(length = 100, nullable = false)
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity gameEntity;

    public static GameFileEntity toSaveGameThumbnailEntity(GameEntity gameEntity, String originalFileName, String storedFileName){
        GameFileEntity gameFileEntity = new GameFileEntity();
        gameFileEntity.setGameEntity(gameEntity);
        gameFileEntity.setOriginalFileName(originalFileName);
        gameFileEntity.setStoredFileName(storedFileName);
        return gameFileEntity;
    }
}
