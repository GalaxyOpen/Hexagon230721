package com.icia.hexagon.Controller;

import com.icia.hexagon.Config.Security.PasswordUtils;
import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;


    // 회원가입 화면출력
    @GetMapping("/save")
    public String saveForm(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "/memberPages/memberSave";
        } else {
            return "redirect:/";
        }
    }

    // 회원가입 정보처리
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        String encodedPassword = PasswordUtils.encryptPassword(memberDTO.getMemberPassword());  // 비밀번호 암호화
        memberDTO.setMemberPassword(encodedPassword);  // 암호화된 비밀번호로 설정
        memberService.save(memberDTO);
        return "redirect:/";
    }

    // 회원가입 아이디 중복체크
    @PostMapping("/dup-check")
    public ResponseEntity IDCheck(@RequestBody MemberDTO memberDTO){
        boolean result = memberService.IDCheck(memberDTO.getMemberEmail());
        if (result) {
            return  new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // 회원목록 (관리자만 접근가능)
    @Transactional
    @GetMapping
    public String findAll(@PageableDefault(page=1)Pageable pageable,
                          @RequestParam(value="type", required=false, defaultValue = "")String type,
                          @RequestParam(value="q", required = false, defaultValue = "")String q,
                          Model model,
                          @AuthenticationPrincipal User user){
        Page<MemberDTO> memberDTOPage = memberService.paging(pageable, type, q);
        if(memberDTOPage.getTotalElements()==0){
            model.addAttribute("memberList", null);
        }else{
            model.addAttribute("memberList", memberDTOPage);
        }
        int blockLimit=5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < memberDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : memberDTOPage.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        if(user.getUsername().equals("admin")) {
            return "/memberPages/memberList";
        } else {
            return "redirect:/";
        }
    }

    // 로그인 화면
    @GetMapping("/login")
    public String loginForm(@AuthenticationPrincipal User user){
        if(user != null) {
            return "redirect:/";
        } else {
            return "/memberPages/memberLogin";
        }
    }

    // 로그인 실패 화면
    @GetMapping("/login/")
    public String failLoginForm(HttpServletRequest request, Model model) {
        // 실패한 아이디를 세션에서 가져오기
        String memberId = (String) request.getSession().getAttribute("SPRING_SECURITY_LAST_USERNAME");
        MemberDTO memberDTO = memberService.findByMemberId(memberId);

        System.out.println("실패한 아이디 정보 = " + memberDTO);
        if (memberDTO != null) {
            model.addAttribute("member", memberDTO);
        } else {
            model.addAttribute("member", null);
        }

        return "/memberPages/memberLoginFail";
    }


    // 마이페이지
    @GetMapping("/myPage")
    public String myPage(){
        return "/memberPages/memberMain";
    }


    // 회원상세 정보
    @GetMapping("/detail")
    public String detail(@AuthenticationPrincipal User user, Model model){
        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        model.addAttribute("user", memberDTO);
        return "/memberPages/memberDetail";
    }

    // 회원상세 정보 (관리자만 접근가능)
    @GetMapping("/{id}")
    public String membersDetail(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("user", memberDTO);
        return "/memberPages/memberDetail";
    }


    // 회원정보 수정화면
    @GetMapping("/update")
    public String updateForm(@AuthenticationPrincipal User user, Model model) {
        MemberDTO memberDTO = memberService.findByMemberId(user.getUsername());
        model.addAttribute("user", memberDTO);
        return "/memberPages/memberUpdate";
    }


    // 회원정보 수정처리
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/";
    }



    // 회원정보 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id, HttpServletRequest request){
        MemberDTO memberDTO = memberService.findById(id);
        memberService.delete(memberDTO);

        // 로그아웃 수행
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
