package com.icia.hexagon.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name="chat_table")
public class ChatMessageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length=20, nullable = false)
    private String chatWriter;

    @Column(length=1000, nullable = false)
    private String chatContents;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="roomId")
    private ChatRoomEntity chatRoomEntity;
}
