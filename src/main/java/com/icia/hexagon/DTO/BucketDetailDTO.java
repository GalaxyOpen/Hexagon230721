package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.BucketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketDetailDTO {
    private Long id;
    private String memberId; // 회원번호
    private Long gameId; // 게임번호

    // 장바구니에서 보여줄 것
//    private String createdAt; // 구매일자
    private String gameTitle;  // 출시가
    private Long releasePrice;  // 출시가
    private int discountRate;   // 할인율
    private Long salesPrice;  // 판매가

    private String thumbnailUrl;
    private int buyAmount; // 결제금액
//    private String payMethod; // 결제방법
//    private String status; // 구매 상태(완료, 취소, 환불등)

    public static BucketDetailDTO toBucketDetailDTO(BucketEntity bucketEntity){
        BucketDetailDTO bucketDetailDTO = new BucketDetailDTO();

        bucketDetailDTO.setId(bucketEntity.getId());
        bucketDetailDTO.setMemberId(bucketEntity.getMemberEntity().getMemberId());
        bucketDetailDTO.setGameId(bucketEntity.getGameEntity().getId());
        bucketDetailDTO.setGameTitle(bucketEntity.getGameEntity().getGameTitle());
        bucketDetailDTO.setReleasePrice(bucketEntity.getGameEntity().getReleasePrice());
        bucketDetailDTO.setDiscountRate(bucketEntity.getGameEntity().getDiscountRate());
        bucketDetailDTO.setSalesPrice(bucketEntity.getGameEntity().getSalesPrice());
        bucketDetailDTO.setThumbnailUrl(bucketEntity.getGameEntity().getThumbnailUrl());
        bucketDetailDTO.setBuyAmount(bucketEntity.getBuyAmount());
        return bucketDetailDTO;
    }
}