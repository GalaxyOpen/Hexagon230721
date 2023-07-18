package com.icia.hexagon.Entity;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.PointDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "point_table")
@Getter
@Setter

public class PointEntity extends BaseEntity{
// BaseEntity에서 createdAt 상속 (7.17. 이문정)
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

    public static PointEntity toPointSaveEntity(PointDTO pointDTO, MemberEntity memberEntity){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setUsedPoint(pointDTO.getUsedPoint());
        pointEntity.setChargedPoint(pointDTO.getChargedPoint());
        pointEntity.setTotalPoint(pointDTO.getTotalPoint());
        pointEntity.setMemberEntity(memberEntity);
        return pointEntity;
    }

    public static PointEntity toPointPurchaseEntity(MemberEntity memberEntity, GameDTO gameDTO){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setUsedPoint(gameDTO.getSalesPrice());
        pointEntity.setChargedPoint(0L);
        pointEntity.setTotalPoint(memberEntity.getTotalPoint());
        pointEntity.setMemberEntity(memberEntity);
        return pointEntity;
    }

}
