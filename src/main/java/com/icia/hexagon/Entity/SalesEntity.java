package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sales_table")
@Getter
@Setter

public class SalesEntity extends BaseEntity{

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
    private int salesAmount;

    @Column(length = 20, nullable = false)
    private String payMethod;

    @Column(length = 20, nullable = false)
    private String status;

//    public static SalesEntity toSalesSaveEntity(SalesDTO salesDTO){
//        SalesEntity salesEntity = new SalesEntity();
//        salesEntity.setMemberEntity(salesDTO.getMemberEntity);
//        salesEntity.setGameEntity(salesDTO.getGameEntity);
//        salesEntity.setSalesAmount(salesDTO.getSalesAmount);
//        salesEntity.setPayMethod(salesDTO.getPayMethod);
//        salesEntity.setStatus(salesDTO.getStatus);
//        return purchaseEntity;
//    }
}
