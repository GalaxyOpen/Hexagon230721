package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="chat_room_table")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length=100, nullable = false)
    private String roomName;

    @Column(length = 20, nullable = false)
    private String roomReceiver;

    @Column(length = 20, nullable = false)
    private String roomId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;


    @OneToMany(mappedBy="chatRoomEntity", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();
}
