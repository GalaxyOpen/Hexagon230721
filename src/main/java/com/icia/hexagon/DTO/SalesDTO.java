package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SalesDTO {
    private Long id;
    private Long memberId; // 회원번호
    private Long gameId; // 게임번호
    private String createdAt; // 판매일자
    private int salesAmount; // 결제금액
    private String payMethod; // 결제방법
    private String status; // 구매 상태(완료, 취소, 환불등)
}
