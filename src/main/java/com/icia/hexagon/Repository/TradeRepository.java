package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.TradeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<TradeEntity, Long> {

    Optional<TradeEntity> findByMemberEntity_IdAndGameEntity_Id(Long memberId, Long gameId);

    Page<TradeEntity> findByMemberEntity_Id(Long memberId, Pageable pageable);

    Page<TradeEntity> findByGameEntity_IdIn(Collection<Long> gameIds, Pageable pageable);
}
