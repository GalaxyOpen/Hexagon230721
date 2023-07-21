package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.BucketDetailDTO;
import com.icia.hexagon.DTO.BucketSaveDTO;
import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.Service.BucketService;
import com.icia.hexagon.Service.GameService;
import com.icia.hexagon.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bucket")
public class BucketController {
    private final BucketService bucketService;
    private final MemberService memberService;
    private final GameService gameService;

    //장바구니 담기
    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody BucketSaveDTO bucketSaveDTO) {
        if (bucketService.check(bucketSaveDTO)) {
            bucketService.save(bucketSaveDTO);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/")
    public String findById(@AuthenticationPrincipal User user,
                           Model model){

        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        List<BucketDetailDTO> memberId = bucketService.findByMemberId(memberDTO.getMemberId());

        boolean bucket = false;

        if(!memberId.isEmpty()){
            int totalPrice=0;
            for(BucketDetailDTO b : memberId){
                totalPrice += b.getBuyAmount();
            }
            model.addAttribute("bucketList", memberId);
            model.addAttribute("totalPrice", totalPrice);


            Long totalPoint = memberDTO.getTotalPoint();
//            GameDTO gameDTO = gameService.findById(id);
            model.addAttribute("totalPoint", totalPoint);
//            model.addAttribute("member", memberDTO);
//            model.addAttribute("game", gameDTO);

        }else{
            //비어있을때
            bucket = true;
        }
        model.addAttribute("bucket", bucket);
        return "gamePages/gameBucket";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id){
        bucketService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}