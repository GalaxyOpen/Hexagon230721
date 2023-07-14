package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.GameReviewDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.ThumbnailDTO;
import com.icia.hexagon.Service.GameReviewService;
import com.icia.hexagon.Service.GameService;
import com.icia.hexagon.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final MemberService memberService;
    private final GameReviewService gameReviewService;

    // 게임등록 화면
    @GetMapping("/save")
    public String saveForm(){
        return "/gamePages/gameSave";
    }

    // 게임등록 처리
    @PostMapping("/save")
    public String save(@ModelAttribute GameDTO gameDTO) throws IOException {
       gameService.save(gameDTO);
       return "redirect:/game";
    }

    // 게임목록
    @GetMapping
    public String findAll(@PageableDefault(page=1) Pageable pageable,
                          @RequestParam(value="type", required = false, defaultValue = "")String type,
                          @RequestParam(value="q", required = false, defaultValue = "")String q,
                          Model model){
        Page<GameDTO> gameDTOS = gameService.paging(pageable, type, q);
        List<GameDTO> gameList = gameDTOS.getContent();
        List<ThumbnailDTO> thumbnailList = new ArrayList<>();

        for (GameDTO game : gameList) {
            ThumbnailDTO thumbnailDTO = gameService.GameThumbnails(game.getId());
            thumbnailList.add(thumbnailDTO);
        }
        if(gameDTOS.getTotalElements()==0){
            model.addAttribute("gameList",null);
        }else{
            model.addAttribute("thumbnailList", thumbnailList);
            model.addAttribute("gameList", gameDTOS);
        }
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < gameDTOS.getTotalPages()) ? startPage + blockLimit - 1 : gameDTOS.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "/gamePages/gamePagingList";
    }

    // 게임 출시일(최근 **일 이내) 목록
    @GetMapping("/release")
    public String Release(@PageableDefault(page=1) Pageable pageable,
                          @RequestParam(value="type", required = false, defaultValue = "")String type,
                          @RequestParam(value="q", required = false, defaultValue = "")String q,
                          Model model){
        Page<GameDTO> gameDTOS = gameService.release(pageable, type, q);

        if(gameDTOS.getTotalElements()==0){
            model.addAttribute("gameList",null);
        }else{
            model.addAttribute("gameList", gameDTOS);
        }
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < gameDTOS.getTotalPages()) ? startPage + blockLimit - 1 : gameDTOS.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "/gamePages/gameRelease";
    }

    // 할인 게임 리스트
    @GetMapping("/discount")
    public String discount(@PageableDefault(page = 1) Pageable pageable,
                           @RequestParam(value = "type", required = false, defaultValue = "") String type,
                           @RequestParam(value = "q", required = false, defaultValue = "") String q,
                           Model model) {
        Page<GameDTO> gameDTOS = gameService.discount(pageable, type, q);
        if (gameDTOS.getTotalElements() == 0) {
            model.addAttribute("gameList", null);
        } else {
            model.addAttribute("gameList", gameDTOS);
        }
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < gameDTOS.getTotalPages()) ? startPage + blockLimit - 1 : gameDTOS.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        GameDTO firstGame = gameDTOS.getContent().get(0);
        return "/gamePages/gameDiscount";
    }

    // 게임 상세조회
    @Transactional
    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id,
                           @RequestParam(value="page", required = false, defaultValue = "0") int page,
                           @RequestParam("type") String type,
                           @RequestParam("q") String q,
                           Model model){
        model.addAttribute("page",page);
        model.addAttribute("type",type);
        model.addAttribute("q",q);

        GameDTO gameDTO = gameService.findById(id);
        model.addAttribute("game", gameDTO);

        List<GameReviewDTO> gameReviewDTOList = gameReviewService.findAll(id);
        if(gameReviewDTOList.size() > 0) {
            model.addAttribute("gameReviewList", gameReviewDTOList);
        }else{
            model.addAttribute("gameReviewList", null);
        }
        return "/gamePages/gameDetail";
    }

    // 게임정보 수정화면
    @Transactional
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        GameDTO gameDTO = gameService.findById(id);
        model.addAttribute("game", gameDTO);
        return "/gamePages/gameUpdate";
    }

    // 게임정보 수정처리
    @Transactional
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute GameDTO gameDTO) throws IOException{
        gameService.update(gameDTO);
        return "redirect:/game";
    }

    // 게임정보 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        gameService.delete(id);
        return "redirect:/game";
    }



}
