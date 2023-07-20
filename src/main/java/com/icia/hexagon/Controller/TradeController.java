package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.GameDTO;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.DTO.TradeDTO;
import com.icia.hexagon.Service.GameService;
import com.icia.hexagon.Service.MemberService;
import com.icia.hexagon.Service.PointService;
import com.icia.hexagon.Service.TradeService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

    private final MemberService memberService;
    private final GameService gameService;
    private final TradeService tradeService;
    private final PointService pointService;

    @Transactional
    @GetMapping("/save/{id}")
    public String purchaseSave(@PathVariable Long id,
                               @AuthenticationPrincipal User user) {
        GameDTO gameDTO = gameService.findById(id);
        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());

        pointService.pointPurchase(memberDTO, gameDTO);
        tradeService.tradeSave(gameDTO, memberDTO);
        return "redirect:/game";
    }

    @Transactional
    @GetMapping("/purchase")
    public String purchaseHistory(@PageableDefault(page = 1) Pageable pageable,
                                  @AuthenticationPrincipal User user, Model model) {

        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        Page<TradeDTO> purchaseHistory = tradeService.purchaseHistory(pageable, memberDTO.getId());

        List<Long> gameIds = new ArrayList<>();
        for (TradeDTO tradeDTO : purchaseHistory.getContent()) {
            gameIds.add(tradeDTO.getGameId());
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

        model.addAttribute("games", games);

        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < purchaseHistory.getTotalPages()) ? startPage + blockLimit - 1 : purchaseHistory.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/tradePages/purchase";
    }

    @Transactional
    @GetMapping("/sales")
    public String salesHistory(@PageableDefault(page = 1) Pageable pageable,
                               @AuthenticationPrincipal User user, Model model) {

        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        List<GameDTO> gameDTOList = gameService.findByMemberId(memberDTO.getId());

        // gameDTOList에서 gameIds 가져오기
        List<Long> gameIds = new ArrayList<>();
        for (GameDTO gameDTO : gameDTOList) {
            gameIds.add(gameDTO.getId());
        }

        Page<TradeDTO> purchaseHistory = tradeService.salesHistory(pageable, gameIds);

        // purchaseHistory에서 gameTitle 가져오기
        List<String> gameTitle = new ArrayList<>();
        for (TradeDTO tradeDTO : purchaseHistory.getContent()) {
            GameDTO game = gameService.findById(tradeDTO.getGameId());
            gameTitle.add(game.getGameTitle());
        }

        // purchaseHistory에서 memberIdsInPurchaseHistory 가져오기
        List<String> memberId = new ArrayList<>();
        for (TradeDTO tradeDTO : purchaseHistory.getContent()) {
            MemberDTO member = memberService.findById(tradeDTO.getMemberId());
            memberId.add(member.getMemberId());
        }

        model.addAttribute("gameTitle", gameTitle);
        model.addAttribute("memberId", memberId);

        if (purchaseHistory.getTotalElements() == 0) {
            model.addAttribute("purchaseList", null);
        } else {
            model.addAttribute("purchaseList", purchaseHistory);
        }

        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < purchaseHistory.getTotalPages()) ? startPage + blockLimit - 1 : purchaseHistory.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/tradePages/sales";
    }

}
