package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="member_photo_table")
public class MemberPhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 100)
    private String OriginalFileName;

    @Column(length = 100)
    private String StoredFileName;

    @Column
    private Long memberId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="memberId")
    private MemberEntity memberEntity;
}
