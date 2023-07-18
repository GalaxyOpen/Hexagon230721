package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.BucketEntity;
import com.icia.hexagon.Entity.PurchaseEntity;
import com.icia.hexagon.Util.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private Long memberId; // 회원번호
    private Long gameId; // 게임번호
    private String createdAt; // 구매일자
    private Long buyAmount; // 결제금액

    public static PurchaseDTO toPurchaseDTO(PurchaseEntity purchaseEntity){
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchaseEntity.getId());
        purchaseDTO.setMemberId(purchaseEntity.getMemberEntity().getId());
        purchaseDTO.setGameId(purchaseEntity.getGameEntity().getId());
        purchaseDTO.setCreatedAt(UtilClass.dateFormat(purchaseEntity.getCreatedAt()));
        purchaseDTO.setBuyAmount(purchaseEntity.getBuyAmount());
        return purchaseDTO;
    }
}
