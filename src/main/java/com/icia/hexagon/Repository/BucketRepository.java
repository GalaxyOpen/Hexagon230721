package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.BucketEntity;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketRepository extends JpaRepository<BucketEntity, Long> {

    Optional<BucketEntity> findByMemberEntityAndGameEntity(MemberEntity memberEntity, GameEntity gameEntity);

    List<BucketEntity> findByMemberEntity_Id(Long id);

    List<BucketEntity> findByMemberEntity_MemberId(String memberId);
    void deleteAllByMemberEntity_Id(Long memberId);



}