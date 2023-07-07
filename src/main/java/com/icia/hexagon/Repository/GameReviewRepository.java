package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.GameReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameReviewRepository extends JpaRepository<GameReviewEntity, Long> {
    List<GameReviewEntity> findByGameEntityOrderByIdDesc(GameEntity gameEntity);
}
