package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PointDTO;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Entity.PointEntity;
import com.icia.hexagon.Repository.GameRepository;
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

import java.util.Optional;

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
                .status(pointEntity.getStatus())
                .createdAt(UtilClass.dateFormat(pointEntity.getCreatedAt()))
                .build());
        return pointDTOS;
    }

    public void pointPurchase(MemberDTO memberDTO, GameDTO gameDTO) {
        // gameDTO.getSalesPrice()가 0인 경우, 작동하지 않도록 조건을 추가합니다.
        if (gameDTO.getSalesPrice() == 0) {
            return; // 메소드를 종료하고 더 이상 실행하지 않습니다.
        }

        MemberEntity memberEntity = MemberEntity.toPurchaseEntity(memberDTO, gameDTO);
        PointEntity pointEntity = PointEntity.toPointPurchaseEntity(memberEntity, gameDTO);
        memberRepository.save(memberEntity);
        pointRepository.save(pointEntity);
    }

    public void pointSales(GameDTO gameDTO) {
        // gameDTO.getSalesPrice()가 0인 경우, 작동하지 않도록 조건을 추가합니다.
        if (gameDTO.getSalesPrice() == 0) {
            return; // 메소드를 종료하고 더 이상 실행하지 않습니다.
        }

        Optional<MemberEntity> member = memberRepository.findById(gameDTO.getMemberId());
        MemberEntity memberEntity = MemberEntity.toSalesEntity(MemberDTO.toDTO(member.get()), gameDTO);
        PointEntity pointEntity = PointEntity.toPointSalesEntity(member.get(), gameDTO);
        memberRepository.save(memberEntity);
        pointRepository.save(pointEntity);
    }
}
