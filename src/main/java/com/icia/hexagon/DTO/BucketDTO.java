package com.icia.hexagon.DTO;

import com.icia.hexagon.Entity.GameEntity;
import com.icia.hexagon.Entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BucketDTO {

    private Long id; // 장바구니 번호
    private Long MemberId; // 회원 번호
    private GameEntity GameId; // 게임 번호

}
