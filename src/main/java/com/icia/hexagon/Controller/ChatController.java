package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.ChatRoomDTO;
import com.icia.hexagon.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/member/chat")
    private String chat(){
        return "/memberPages/chat";
    }

    @PostMapping("/chat/enter")
    public ChatRoomDTO createRoom(@RequestParam String name){
        return chatService.createRoom(name);
    }
    @GetMapping("/chat/message")
    public List<ChatRoomDTO> findAllRooms(){
        return chatService.findAllRoom();
    }
}
