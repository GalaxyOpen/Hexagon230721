package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.GameReviewDTO;
import com.icia.hexagon.Service.GameReviewService;
import com.icia.hexagon.Service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final GameReviewService gameReviewService;

    @GetMapping("/save")
    public String saveForm(){
        return "/gamePages/gameSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute GameDTO gameDTO) throws IOException {
       gameService.save(gameDTO);
       return "redirect:/game";
    }
    @GetMapping
    public String findAll(@PageableDefault(page=1) Pageable pageable,
                          @RequestParam(value="type", required = false, defaultValue = "")String type,
                          @RequestParam(value="q", required = false, defaultValue = "")String q,
                          Model model){
        Page<GameDTO> gameDTOS = gameService.paging(pageable, type, q);
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
        return "/gamePages/gamePagingList";
    }
    @Transactional
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id,
                           @RequestParam(value="page", required = false, defaultValue = "0") int page,
                           @RequestParam("type") String type,
                           @RequestParam("q") String q,
                           Model model){
        model.addAttribute("page",page);
        model.addAttribute("type",type);
        model.addAttribute("q",q);
        try{
            GameDTO gameDTO = gameService.findById(id);
            model.addAttribute("game", gameDTO);
            List<GameReviewDTO> gameReviewDTOList = gameReviewService.findAll(id);
            if(gameReviewDTOList.size() > 0) {
                model.addAttribute("gameReviewList", gameReviewDTOList);
            }else{
                model.addAttribute("gameReviewList", null);
            }
            return "/gamePages/gameDetail";
        }catch(NoSuchElementException e){
            return "/gamepages/gameNotFound";
        }
    }
    @Transactional
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        GameDTO gameDTO = gameService.findById(id);
        model.addAttribute("game", gameDTO);
        return "/gamePages/gameUpdate";
    }

    @Transactional
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute GameDTO gameDTO) throws IOException{
        gameService.update(gameDTO);
        return "redirect:/game";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        gameService.delete(id);
        return "redirect:/game";
    }



}
