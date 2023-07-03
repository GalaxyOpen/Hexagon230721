package com.icia.hexagon.Entity;
import com.icia.hexagon.DTO.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="member_table")

public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 30, unique = true)
    private String memberId;

    @Column(length = 30, nullable = false)
    private String memberPassword;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 50, nullable = false)
    private String memberEmail;

    @Column(length=20, nullable = false)
    private String memberMobile;


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

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        return memberEntity;
    }
    public static MemberEntity toUpdateEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        return memberEntity;
    }
}
