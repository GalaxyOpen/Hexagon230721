package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BucketSaveDTO {

    private Long id; // 장바구니 번호
    private Long MemberId; // 회원 번호
    private Long GameId; // 게임 번호

}
