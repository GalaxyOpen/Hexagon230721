package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.Service.GameService;
import com.icia.hexagon.Service.MemberService;
import com.icia.hexagon.Service.PointService;
import com.icia.hexagon.Service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    private final MemberService memberService;
    private final GameService gameService;
    private final PurchaseService purchaseService;
    private final PointService pointService;

    @Transactional
    @GetMapping("/save/{id}")
    public String purchaseSave(@PathVariable Long id,
                               @AuthenticationPrincipal User user) {
        GameDTO gameDTO = gameService.findById(id);
        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());

        pointService.pointPurchase(memberDTO, gameDTO);
        purchaseService.purchaseSave(gameDTO, memberDTO);
        return "redirect:/game";
    }

}
