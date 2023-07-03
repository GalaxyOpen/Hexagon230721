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
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoomDTO createRoom(@RequestParam String name){
        return chatService.createRoom(name);
    }
    @GetMapping
    public List<ChatRoomDTO> findAllRooms(){
        return chatService.findAllRoom();
    }
}
