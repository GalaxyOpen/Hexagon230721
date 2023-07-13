package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberId(String loginId);
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    Page<MemberEntity> findByMemberNameContaining(String q, PageRequest id);
    Page<MemberEntity> findByMemberIdContaining(String q, PageRequest id);
}
