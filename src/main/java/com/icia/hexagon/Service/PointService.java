package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PointDTO;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Entity.PointEntity;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Repository.PointRepository;
import com.icia.hexagon.Util.UtilClass;
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
    public Page<PointDTO> pointHistory(Pageable pageable, Long memberId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        Page<PointEntity> pointEntities = null;

        pointEntities = pointRepository.findByMemberEntity_Id(memberId, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<PointDTO> pointDTOS = pointEntities.map(pointEntity -> PointDTO.builder()
                .id(pointEntity.getId())
                .memberId(pointEntity.getMemberEntity().getId())
                .ChargedPoint(pointEntity.getChargedPoint())
                .UsedPoint(pointEntity.getUsedPoint())
                .totalPoint(pointEntity.getTotalPoint())
                .createdAt(UtilClass.dateFormat(pointEntity.getCreatedAt())) // point 내역 발생일시 (7.17. 이문정)
                .build());
        return pointDTOS;
    }

    public void pointPurchase(MemberDTO memberDTO, GameDTO gameDTO) {
        MemberEntity memberEntity = MemberEntity.toPurchaseEntity(memberDTO, gameDTO);
        PointEntity pointEntity = PointEntity.toPointPurchaseEntity(memberEntity, gameDTO);
        memberRepository.save(memberEntity);
        pointRepository.save(pointEntity);
    }
}
