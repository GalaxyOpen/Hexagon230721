package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessageEntity,Long> {
    List<ChatMessageEntity> findAllByChatRoomEntity_RoomId(String roomId);
    // 방의 고유 주소를 통해 채팅이력을 모두 다 불러옴
//    List<ChatMessageEntity> findAllByChatRoomEntity_RoomId(String roomId);


}