package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PointDTO;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Entity.PointEntity;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    public void pointCharge(PointDTO pointDTO, MemberDTO memberDTO) {

        MemberEntity memberEntity = MemberEntity.toPointEntity(memberDTO, pointDTO);
        PointEntity pointEntity = PointEntity.toPointSaveEntity(pointDTO, memberEntity);
        memberRepository.save(memberEntity);
        pointRepository.save(pointEntity);
    }
}
