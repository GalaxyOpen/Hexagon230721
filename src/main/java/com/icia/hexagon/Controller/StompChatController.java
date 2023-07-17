package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.ChatMessageDetailDTO;
import com.icia.hexagon.DTO.ChatMessageSaveDTO;
import com.icia.hexagon.Entity.ChatMessageEntity;
import com.icia.hexagon.Entity.ChatRoomEntity;
import com.icia.hexagon.Repository.ChatRepository;
import com.icia.hexagon.Repository.ChatRoomRepository;
import com.icia.hexagon.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompChatController {
    private final SimpMessagingTemplate template; // 특정 브로커로 메세지를 전달
    private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    // Client 가 Send 가능한 경로
    // stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨.
    // " /pub/chat/enter"

    @MessageMapping(value="/chat/enter")
    public void enter(ChatMessageDetailDTO message){
        //set 되기 전
        String enterMember = message.getWriter();

        //채팅 이력 보여주기
        List<ChatMessageDetailDTO> chatList = chatService.findAllChatByRoomId(message.getRoomId());
        if(!chatList.isEmpty()){
            for(ChatMessageDetailDTO c : chatList){
                message.setWriter(c.getWriter());
                message.setMessage(c.getMessage());
                template.convertAndSend("/sub/chat/room"+message.getRoomId(), message);
            }
            message.setMessage(message.getWriter()+"님이 채팅방에 참여하였습니다.");
            message.setWriter(enterMember);
            template.convertAndSend("/sub/chat/room/"+message.getRoomId(), message);

        }else{
            // 채팅방 참여 정보 알림
            message.setMessage(message.getWriter()+"님이 채팅방에 참여하였습니다.");
            template.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
        }
        //채팅 이력 저장하기
        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO(message.getRoomId(), message.getWriter(), message.getMessage());
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(message.getRoomId());
        chatRepository.save(ChatMessageEntity.toChatEntity(chatMessageSaveDTO,chatRoomEntity));



    }
    // 메시지를 보내는 메소드
    @MessageMapping(value="/chat/message")
    public void message(ChatMessageDetailDTO message){
        template.convertAndSend("/sub/chat/room/"+message.getRoomId(), message);

        //DB 채팅한 내용들 저장
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(message.getRoomId());
        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO(message.getRoomId(), message.getWriter(), message.getMessage());

        //레포지로 보냄
        chatRepository.save(ChatMessageEntity.toChatEntity(chatMessageSaveDTO,chatRoomEntity));
    }


}