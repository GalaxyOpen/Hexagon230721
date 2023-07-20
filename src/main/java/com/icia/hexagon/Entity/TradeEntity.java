package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "trade_table")
@Getter
@Setter
public class TradeEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity gameEntity;

    @Column(nullable = false)
    private Long buyAmount;

//    @Column(length = 20, nullable = false)
//    private String payMethod;
//
//    @Column(length = 20, nullable = false)
//    private String status;

    public static TradeEntity toPurchaseEntity(GameEntity gameEntity, MemberEntity memberEntity){
        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setMemberEntity(memberEntity);
        tradeEntity.setGameEntity(gameEntity);
        tradeEntity.setBuyAmount(gameEntity.getSalesPrice());
        return tradeEntity;
    }

}
