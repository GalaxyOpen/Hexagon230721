package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PointDTO;
import com.icia.hexagon.Service.MemberService;
import com.icia.hexagon.Service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @GetMapping("/history")
    public String pointHistory(@PageableDefault(page = 1) Pageable pageable,
                               @AuthenticationPrincipal User user, Model model) {
        Page<PointDTO> pointDTOPage = pointService.pointHistory(pageable);
        System.out.println("포인트 내역 == " + pointDTOPage);
        if (pointDTOPage.getTotalElements() == 0) {
            model.addAttribute("pointList", null);
        } else {
            model.addAttribute("pointList", pointDTOPage);
        }
        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < pointDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : pointDTOPage.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/pointPages/pointHistory";
    }
}
