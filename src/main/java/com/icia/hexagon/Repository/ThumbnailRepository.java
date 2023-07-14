package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.ThumbnailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThumbnailRepository extends JpaRepository<ThumbnailEntity, Long> {
    Optional<ThumbnailEntity> findByGameEntity_Id(Long gameId);

}
