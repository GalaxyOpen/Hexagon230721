package com.icia.hexagon.Repository;



import com.icia.hexagon.Entity.PointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PointRepository extends JpaRepository<PointEntity, Long> {
    Page<PointEntity> findByMemberEntity_Id(Long memberId, Pageable pageable);
}
