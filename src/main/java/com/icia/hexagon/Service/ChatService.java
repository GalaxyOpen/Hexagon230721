package com.icia.hexagon.Service;


import com.icia.hexagon.DTO.ChatMessageDetailDTO;

import com.icia.hexagon.DTO.ChatRoomDetailDTO;
import com.icia.hexagon.Entity.ChatMessageEntity;
import com.icia.hexagon.Entity.ChatRoomEntity;
import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Repository.ChatRepository;
import com.icia.hexagon.Repository.ChatRoomRepository;
import com.icia.hexagon.Repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public List<ChatRoomEntity> findAllRooms() {
        return chatRoomRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }
    //    채팅방 개설
    public void createChatRoom(String roomName, String memberId) {
        String roomId = UUID.randomUUID().toString();
        Optional<MemberEntity> memberOptional = memberRepository.findByMemberId(memberId);
        MemberEntity memberEntity = memberOptional.orElse(null);
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setMemberEntity(memberEntity);
        chatRoomEntity.setRoomName(roomName);
        chatRoomEntity.setRoomId(roomId);
        System.out.println("name = " + roomName + ", memberId = " + memberId);
        System.out.println("roomId"+roomId);
        chatRoomRepository.save(chatRoomEntity);
    }

    // 채팅룸 생성
//    public void createChatRoom(String roomName){
//        ChatRoomDTO room = ChatRoomDTO.create(roomName);
//        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toChatRoomEntity(room.getRoomName(), room.getRoomId());
//        chatRoomRepository.save(chatRoomEntity);
//    }
    //채팅 방 지우기
    public void deleteById(Long chatRoomId){
        chatRoomRepository.deleteById(chatRoomId);
    }

    //채팅 이력 보기
    public List<ChatMessageDetailDTO> findAllChatByRoomId(String roomId) {
        List<ChatMessageEntity> chatMessageEntityList = chatRepository.findAllByChatRoomEntity_RoomId(roomId);
        List<ChatMessageDetailDTO> chatMessageList = new ArrayList<>();
        for (ChatMessageEntity c:chatMessageEntityList){
            chatMessageList.add(ChatMessageDetailDTO.toChatMessageDetailDTO(c));
        }
        return chatMessageList;
    }




    public ChatRoomDetailDTO findRoomById(String roomId){
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(roomId);
        ChatRoomDetailDTO chatRoomDetailDTO = ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoomEntity);
        return chatRoomDetailDTO;
    }


}
