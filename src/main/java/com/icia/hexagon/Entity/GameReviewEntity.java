package com.icia.hexagon.Entity;

import com.icia.hexagon.DTO.GameReviewDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "game_review_table")
public class GameReviewEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String reviewWriter;

    @Column(length = 500, nullable = false)
    private String reviewContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity gameEntity;

    @Column
    private int star;

    public static GameReviewEntity toSaveEntity(GameEntity gameEntity, GameReviewDTO gameReviewDTO){
        GameReviewEntity gameReviewEntity = new GameReviewEntity();
        gameReviewEntity.setReviewWriter(gameReviewDTO.getReviewWriter());
        gameReviewEntity.setReviewContents(gameReviewDTO.getReviewContents());
        gameReviewEntity.setGameEntity(gameEntity);
        gameReviewEntity.setStar(gameReviewDTO.getStar());

        return gameReviewEntity;
    }
}
