package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.ChatRoomDTO;
import com.icia.hexagon.DTO.ChatRoomDetailDTO;
import com.icia.hexagon.Entity.ChatRoomEntity;
import com.icia.hexagon.Repository.ChatRoomRepository;
import com.icia.hexagon.Service.ChatService;
import com.icia.hexagon.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MemberService memberService;
    private final ChatRoomRepository chatRoomRepository;
    //채팅방 목록 조회
    @GetMapping(value="/rooms")
    public ModelAndView rooms(){
        log.info("# ALL Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");
        List<ChatRoomEntity> chatRoomList = chatService.findAllRooms();
        mv.addObject("list", chatRoomList);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        mv.addObject("username", username);
        return mv;
    }


    //채팅방 개설
    @PostMapping(value = "/room")
    public ModelAndView createRoom(@RequestParam String roomName,@RequestParam String memberId, RedirectAttributes rttr) {
        log.info("#Create chat room, roomName: " + roomName);
        chatService.createChatRoom(roomName,memberId);
        ModelAndView mav = new ModelAndView("redirect:/chat/rooms");
        ChatRoomEntity room = new ChatRoomEntity();
        mav.addObject("room", room);
        mav.addObject("username", memberId);
        rttr.addFlashAttribute("roomName", roomName);
        return mav;
    }

    //채팅방 조회
    @GetMapping("/room")
    public ModelAndView getRoom(String roomId, Model model, Authentication authentication, RedirectAttributes rttr){
        log.info("# get Chat room, roomId : "+roomId);
        ModelAndView mv = new ModelAndView("chat/room");
        ChatRoomDetailDTO roomDetail = chatService.findRoomById(roomId);
        String username = authentication.getName();
        rttr.addFlashAttribute("username", username);
        model.addAttribute("room", roomDetail);
        return mv;
    }
}
