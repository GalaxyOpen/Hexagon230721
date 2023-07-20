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

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static PointEntity toPointSaveEntity(PointDTO pointDTO, MemberEntity memberEntity){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setUsedPoint(pointDTO.getUsedPoint());
        pointEntity.setChargedPoint(pointDTO.getChargedPoint());
        pointEntity.setTotalPoint(pointDTO.getTotalPoint());
        pointEntity.setStatus("포인트충전");
        pointEntity.setMemberEntity(memberEntity);
        return pointEntity;
    }

    public static PointEntity toPointPurchaseEntity(MemberEntity memberEntity, GameDTO gameDTO){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setUsedPoint(gameDTO.getSalesPrice());
        pointEntity.setChargedPoint(0L);
        pointEntity.setTotalPoint(memberEntity.getTotalPoint());
        pointEntity.setStatus("게임구매");
        pointEntity.setMemberEntity(memberEntity);
        return pointEntity;
    }

    public static PointEntity toPointSalesEntity(MemberEntity entity ,GameDTO gameDTO){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setUsedPoint(0L);
        pointEntity.setChargedPoint ((long)(gameDTO.getSalesPrice()*0.9));
        pointEntity.setTotalPoint(entity.getTotalPoint());
        pointEntity.setStatus("게임판매");
        pointEntity.setMemberEntity(entity);
        return pointEntity;
    }
}
