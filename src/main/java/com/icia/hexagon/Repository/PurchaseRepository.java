package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.PointEntity;
import com.icia.hexagon.Entity.PurchaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    Optional<PurchaseEntity> findByMemberEntity_IdAndGameEntity_Id(Long memberId, Long gameId);

    Page<PurchaseEntity> findByMemberEntity_Id(Long memberId, Pageable pageable);
}
