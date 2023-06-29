package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "game_table")
public class GameEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String gameTitle;

    @Column(length = 10, nullable = false)
    private String gameGenre;

    @Column(length = 30, nullable = false)
    private String gameCreator;

    @Column(length = 50, nullable = false)
    private String gameDistr;

    @Column(length = 10, nullable = false)
    private String gameGrade;

    @Column(length = 1000, nullable = false)
    private String gameIntro;

    @Column
    private Long releasePrice;

    @Column
    private int discountRate;

    @Column
    private Long salesPrice;

    @Column
    private int fileAttached;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameFileEntity> gameFileEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameReviewEntity> gameReviewEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StarEntity> starEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BucketEntity> bucketEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchaseEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SalesEntity> salesEntityList = new ArrayList<>();




}
