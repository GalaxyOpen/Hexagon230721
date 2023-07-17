package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PointDTO {

    private Long id; // 포인트 번호
    private Long UsedPoint; // 사용 포인트
    private Long ChargedPoint; // 충전 포인트
    private Long totalPoint; // 현재 포인트
    private Long memberId; // 회원번호
    private String createdAt; // 포인트 내역 발생일시 (7.17. 이문정)
}
