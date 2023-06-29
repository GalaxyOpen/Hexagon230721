package com.icia.hexagon.Entity;

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

//    public static BucketEntity toBucketSaveEntity(BucketDTO bucketDTO){
//        BucketEntity bucketEntity = new BucketEntity();
//        bucketEntity.setMemberEntity(bucketDTO.getMemberEntity());
//        bucketEntity.setGameEntity(bucketDTO.getGameEntity);
//        return bucketEntity;
//    }
}
