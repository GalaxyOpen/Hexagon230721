package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "point_table")
@Getter
@Setter

public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usedPoint;

    @Column(nullable = false)
    private Long chargedPoint;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long totalPoint;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

//    public static PointEntity toPointSaveEntity(PointDTO pointDTO){
//        PointEntity pointEntity = new PointEntity();
//        pointEntity.setUsedPoint(pointDTO.getUsedPoint);
//        pointEntity.setChargedPoint(pointDTO.getChargedPoint);
//        pointEntity.setTotalPoint(pointDTO.getTotalPoint);
//        pointEntity.setMemberEntity(pointDTO.getMemberEntity);
//        return pointEntity;
//    }

}
