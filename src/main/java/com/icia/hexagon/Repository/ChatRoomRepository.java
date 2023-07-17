package com.icia.hexagon.Repository;

import com.icia.hexagon.Entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {
    // 방 주소를 통해 ChatRoomEntity 를 가져오는 로직
    ChatRoomEntity findByRoomId(String roomId);

}
