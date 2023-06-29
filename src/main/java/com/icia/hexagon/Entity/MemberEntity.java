package com.icia.hexagon.Entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="member_table")

public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String memberId;

    @Column(length = 30, nullable = false)
    private String memberPassword;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 50, nullable = false)
    private String memberEmail;

    @Column(length=20, nullable = false)
    private String memberMobile;

    @Column(nullable = false)
    private LocalDateTime memberBirth;

    @OneToMany(mappedBy="memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<MemberPhotoEntity> memberPhotoEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<GameEntity> gameEntityList = new ArrayList<>();

    @OneToMany(mappedBy="memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<BucketEntity> bucketEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade=CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PointEntity> pointEntityList = new ArrayList<>();

    @OneToMany(mappedBy="memberEntity", cascade=CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<GameReviewEntity> gameReviewEntityList = new ArrayList<>();

    @OneToMany(mappedBy ="memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<PurchaseEntity> purchaseEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade=CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SalesEntity> salesEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<StarEntity> starEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch=FetchType.LAZY)
    private List<ChatRoomEntity> chatRoomEntityList = new ArrayList<>();
}
