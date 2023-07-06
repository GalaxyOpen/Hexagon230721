package com.icia.hexagon.Repository;


import com.icia.hexagon.Entity.GameFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameFileRepository extends JpaRepository<GameFileEntity, Long> {
}
