package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;

import com.icia.hexagon.DTO.TradeDTO;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.MemberEntity;

import com.icia.hexagon.Entity.TradeEntity;
import com.icia.hexagon.Repository.GameRepository;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Repository.TradeRepository;
import com.icia.hexagon.Util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;
    private final GameRepository gameRepository;

    @Transactional
    public void tradeSave(GameDTO gameDTO, MemberDTO memberDTO) {

        if(gameDTO.getSalesPrice() <= memberDTO.getTotalPoint()){
            GameEntity gameEntity = gameRepository.findById(gameDTO.getId()).get();
            MemberEntity memberEntity = memberRepository.findById(memberDTO.getId()).get();
            tradeRepository.save(TradeEntity.toPurchaseEntity(gameEntity, memberEntity));
        }
    }

    public TradeDTO findByMemberId(GameDTO gameDTO, MemberDTO memberDTO) {
        Optional<GameEntity> optionalGameEntity = gameRepository.findById(gameDTO.getId());
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberDTO.getId());

        if (optionalGameEntity.isPresent() && optionalMemberEntity.isPresent()) {
            GameEntity gameEntity = optionalGameEntity.get();
            MemberEntity memberEntity = optionalMemberEntity.get();

            Optional<TradeEntity> optionalPurchaseEntity = tradeRepository.findByMemberEntity_IdAndGameEntity_Id(memberEntity.getId(), gameEntity.getId());

            return optionalPurchaseEntity.map(TradeDTO::toPurchaseDTO).orElse(null);
        } else {

            return null;
        }
    }

    public Page<TradeDTO> purchaseHistory(Pageable pageable, Long memberId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        Page<TradeEntity> purchaseEntities = null;

        purchaseEntities = tradeRepository.findByMemberEntity_Id(memberId, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<TradeDTO> purchaseDTOS = purchaseEntities.map(purchaseEntity -> TradeDTO.builder()
                .id(purchaseEntity.getId())
                .memberId(purchaseEntity.getMemberEntity().getId())
                .gameId(purchaseEntity.getGameEntity().getId())
                .createdAt(UtilClass.dateFormat(purchaseEntity.getCreatedAt()))
                .buyAmount(purchaseEntity.getBuyAmount())
                .build());
        return purchaseDTOS;
    }


    public Page<TradeDTO> salesHistory(Pageable pageable, List<Long> gameIds) {

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        Page<TradeEntity> purchaseEntities = null;

        purchaseEntities = tradeRepository.findByGameEntity_IdIn(gameIds, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<TradeDTO> purchaseDTOS = purchaseEntities.map(purchaseEntity -> TradeDTO.builder()
                .id(purchaseEntity.getId())
                .memberId(purchaseEntity.getMemberEntity().getId())
                .gameId(purchaseEntity.getGameEntity().getId())
                .createdAt(UtilClass.dateFormat(purchaseEntity.getCreatedAt()))
                .buyAmount(purchaseEntity.getBuyAmount())
                .build());
        return purchaseDTOS;
    }
}
