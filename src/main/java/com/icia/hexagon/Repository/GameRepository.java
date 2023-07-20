package com.icia.hexagon.Repository;


import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.GameFileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface GameRepository extends JpaRepository<GameEntity, Long> {
    Page<GameEntity> findByGameTitleContaining(String q, PageRequest pageRequest);
    Page<GameEntity> findByGameDistrContaining(String q, PageRequest pageRequest);
    Page<GameEntity> findByGameCreatorContaining(String q, PageRequest pageRequest);

    // 게임출시일(최근30일 이내)
    Page<GameEntity> findByGameTitleContainingAndCreatedAtAfter(String q, LocalDateTime date, Pageable pageable);
    Page<GameEntity> findByGameDistrContainingAndCreatedAtAfter(String q, LocalDateTime date, Pageable pageable);
    Page<GameEntity> findByGameCreatorContainingAndCreatedAtAfter(String q, LocalDateTime date, Pageable pageable);

    // 출시일 이후 게임 리스트
    Page<GameEntity> findAllByCreatedAtAfter(LocalDateTime date, Pageable pageable);


    // 할인게임 내림차순
    Page<GameEntity> findByGameTitleContainingAndDiscountRateGreaterThanOrderByDiscountRateDesc(String q, int discountRate, Pageable pageable);

    Page<GameEntity> findByGameDistrContainingAndDiscountRateGreaterThanOrderByDiscountRateDesc(String q, int discountRate, Pageable pageable);

    Page<GameEntity> findByGameCreatorContainingAndDiscountRateGreaterThanOrderByDiscountRateDesc(String q, int discountRate, Pageable pageable);

    Page<GameEntity> findByDiscountRateGreaterThanOrderByDiscountRateDesc(int discountRate, Pageable pageable);

    List<GameEntity> findByMemberEntity_Id(Long memberId);
}
