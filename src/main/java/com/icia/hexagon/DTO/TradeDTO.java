package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.TradeEntity;
import com.icia.hexagon.Util.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDTO {
    private Long id;
    private Long memberId; // 회원번호
    private Long gameId; // 게임번호
    private String createdAt; // 구매일자
    private Long buyAmount; // 결제금액

    public static TradeDTO toPurchaseDTO(TradeEntity tradeEntity){
        TradeDTO tradeDTO = new TradeDTO();
        tradeDTO.setId(tradeEntity.getId());
        tradeDTO.setMemberId(tradeEntity.getMemberEntity().getId());
        tradeDTO.setGameId(tradeEntity.getGameEntity().getId());
        tradeDTO.setCreatedAt(UtilClass.dateFormat(tradeEntity.getCreatedAt()));
        tradeDTO.setBuyAmount(tradeEntity.getBuyAmount());
        return tradeDTO;
    }
}
