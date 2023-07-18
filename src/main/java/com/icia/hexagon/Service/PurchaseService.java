package com.icia.hexagon.Service;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PurchaseDTO;
import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Entity.PurchaseEntity;
import com.icia.hexagon.Repository.GameRepository;
import com.icia.hexagon.Repository.MemberRepository;
import com.icia.hexagon.Repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;
    private final GameRepository gameRepository;

    @Transactional
    public void purchaseSave(GameDTO gameDTO, MemberDTO memberDTO) {

        if(gameDTO.getSalesPrice() <= memberDTO.getTotalPoint()){
            GameEntity gameEntity = gameRepository.findById(gameDTO.getId()).get();
            MemberEntity memberEntity = memberRepository.findById(memberDTO.getId()).get();
            purchaseRepository.save(PurchaseEntity.toPurchaseEntity(gameEntity, memberEntity));
        }
    }

    public PurchaseDTO findByMemberId(GameDTO gameDTO, MemberDTO memberDTO) {
        Optional<GameEntity> optionalGameEntity = gameRepository.findById(gameDTO.getId());
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberDTO.getId());

        if (optionalGameEntity.isPresent() && optionalMemberEntity.isPresent()) {
            GameEntity gameEntity = optionalGameEntity.get();
            MemberEntity memberEntity = optionalMemberEntity.get();

            Optional<PurchaseEntity> optionalPurchaseEntity = purchaseRepository.findByMemberEntity_IdAndGameEntity_Id(memberEntity.getId(), gameEntity.getId());

            return optionalPurchaseEntity.map(PurchaseDTO::toPurchaseDTO).orElse(null);
        } else {

            return null;
        }
    }
}
