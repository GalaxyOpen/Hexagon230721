package com.icia.hexagon.Repository;


import com.icia.hexagon.Entity.GameFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameFileRepository extends JpaRepository<GameFileEntity, Long> {

    Optional<GameFileEntity> findByGameEntity_Id(Long gameId);
}
