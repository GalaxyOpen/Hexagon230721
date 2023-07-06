package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.Service.GameReviewService;
import com.icia.hexagon.Service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GameReviewService gameReviewService;

    @GetMapping("/game/save")
    public String saveForm(){
        return "/gamePages/gameSave";
    }
    @PostMapping("/game/save")
    public String save(@ModelAttribute GameDTO gameDTO) throws IOException {
       gameService.save(gameDTO);
       return "redirect:/game";
    }


}
