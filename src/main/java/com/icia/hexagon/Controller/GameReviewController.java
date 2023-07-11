package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.GameReviewDTO;
import com.icia.hexagon.Service.GameReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameReviewController {
    private final GameReviewService gameReviewService;
    @PostMapping("/review/save")
    public ResponseEntity save(@RequestBody GameReviewDTO gameReviewDTO){
        try{
            gameReviewService.save(gameReviewDTO);
            List<GameReviewDTO> gameReviewDTOList = gameReviewService.findAll(gameReviewDTO.getGameId());
            System.out.println("gameReviewDTOList = " + gameReviewDTOList);
            return new ResponseEntity<>(gameReviewDTOList, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
