package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.PointDTO;
import com.icia.hexagon.DTO.PurchaseDTO;
import com.icia.hexagon.Service.GameService;
import com.icia.hexagon.Service.MemberService;
import com.icia.hexagon.Service.PointService;
import com.icia.hexagon.Service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    @GetMapping("/history")
    public String purchaseHistory(@PageableDefault(page = 1) Pageable pageable,
                                  @AuthenticationPrincipal User user, Model model) {

        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        Page<PurchaseDTO> purchaseHistory = purchaseService.purchaseHistory(pageable, memberDTO.getId());

        List<Long> gameIds = new ArrayList<>();
        for (PurchaseDTO purchaseDTO : purchaseHistory.getContent()) {
            gameIds.add(purchaseDTO.getGameId());
        }

        // 구매 내역의 모든 게임 ID에 해당하는 게임 정보를 가져옴
        List<String> games = new ArrayList<>();
        for (Long gameId : gameIds) {
            GameDTO game = gameService.findById(gameId);
            if (game != null) {
                games.add(game.getGameTitle());
            }
        }

        if (purchaseHistory.getTotalElements() == 0) {
            model.addAttribute("purchaseList", null);
        } else {
            model.addAttribute("purchaseList", purchaseHistory);
        }

        System.out.println("구매 게임리스트 = " + games);
        model.addAttribute("games", games);

        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < purchaseHistory.getTotalPages()) ? startPage + blockLimit - 1 : purchaseHistory.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/memberPages/memberPurchase";
    }

}
