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

import java.util.ArrayList;
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
        List<BucketDetailDTO> bucketDetailDTOS = bucketService.findByMemberId(memberDTO.getMemberId());

        System.out.println("====== 장바구니 ====== " + bucketDetailDTOS);

        boolean bucket = false;

        if(!bucketDetailDTOS.isEmpty()){

                model.addAttribute("bucketList", bucketDetailDTOS);
                model.addAttribute("totalPrice", memberDTO.getTotalPoint());


            int totalSalesPrice = 0;
            for (BucketDetailDTO bucketDetail : bucketDetailDTOS) {
                totalSalesPrice += bucketDetail.getSalesPrice();
            }

            model.addAttribute("totalSalesPrice", totalSalesPrice);
            model.addAttribute("totalPoint", memberDTO.getTotalPoint());

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