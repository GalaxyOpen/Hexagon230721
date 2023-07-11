package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PointDTO;
import com.icia.hexagon.Service.MemberService;
import com.icia.hexagon.Service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;
    private final MemberService memberService;

    @GetMapping("/charge")
    public String pointChargeForm(@AuthenticationPrincipal User user, Model model){
        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        model.addAttribute("member", memberDTO);
        return "/pointPages/pointCharge";
    }

    @PostMapping("/charge")
    public ResponseEntity<String> pointCharge(@RequestBody PointDTO pointDTO,
                                              @AuthenticationPrincipal User user) {
        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        pointService.pointCharge(pointDTO, memberDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
