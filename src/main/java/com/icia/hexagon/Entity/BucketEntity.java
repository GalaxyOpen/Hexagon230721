package com.icia.hexagon.Entity;

import com.icia.hexagon.DTO.BucketSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bucket_table")
@Getter
@Setter

public class BucketEntity {
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

    public static BucketEntity toBucketSaveEntity(MemberEntity memberEntity, GameEntity gameEntity){
        BucketEntity bucketEntity = new BucketEntity();
        bucketEntity.setMemberEntity(memberEntity);
        bucketEntity.setGameEntity(gameEntity);
        return bucketEntity;
    }
}
