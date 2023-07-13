package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PointDTO;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Entity.PointEntity;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Page<PointDTO> pointHistory(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        Page<PointEntity> pointEntities = null;

        pointEntities = pointRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<PointDTO> pointDTOS = pointEntities.map(pointEntity -> PointDTO.builder()
                .id(pointEntity.getId())
                .memberId(pointEntity.getMemberEntity().getId())
                .ChargedPoint(pointEntity.getChargedPoint())
                .UsedPoint(pointEntity.getUsedPoint())
                .totalPoint(pointEntity.getTotalPoint())
                .build());
        return pointDTOS;
    }
}
