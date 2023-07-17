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
    @Column(name="chatRoom_id")
    private Long id;

    @Column(length = 50)
    private String roomName;

    @Column(length=100)
    private String roomId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;


    @OneToMany(mappedBy="chatRoomEntity", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();

    public static ChatRoomEntity toChatRoomEntity(String roomName,String roomId){
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setRoomName(roomName);
        chatRoomEntity.setRoomId(roomId);

        return chatRoomEntity;
    }
}
