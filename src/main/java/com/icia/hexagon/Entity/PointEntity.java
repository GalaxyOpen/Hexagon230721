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
    private long usedPoint;

    @Column(nullable = false)
    private long chargedPoint;

    @Column(nullable = false)
    @ColumnDefault("0")
    private long totalPoint;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

}
