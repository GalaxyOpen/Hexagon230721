package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "purchase_table")
@Getter
@Setter
public class PurchaseEntity extends BaseEntity{

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
    private int buyAmount;

    @Column(length = 20, nullable = false)
    private String payMethod;

    @Column(length = 20, nullable = false)
    private String status;

//public static PurchaseEntity toPurchaseEntity(PurchaseDTO purchaseDTO){
//    PurchaseEntity purchaseEntity = new PurchaseEntity();
//    purchaseEntity.setMemberEntity(purchaseDTO.getMemberEntity);
//    purchaseEntity.setGameEntity(purchaseDTO.getGameEntity);
//    purchaseEntity.setBuyAmount(purchaseDTO.getBuyAmount);
//    purchaseEntity.setPayMethod(purchaseDTO.getPayMethod);
//    purchaseEntity.setStatus(purchaseDTO.getStatus);
//    return purchaseEntity;
//}

}
