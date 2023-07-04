package com.icia.hexagon.Controller;

import com.icia.hexagon.DTO.MemberDTO;
import com.icia.hexagon.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class
MemberController {
    private final MemberService memberService;
    @GetMapping("/save")
    public String saveForm(){
        return "/memberPages/memberSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "redirect:/member/login";
    }
    @PostMapping("/dup-check")
    public ResponseEntity IDCheck(@RequestBody MemberDTO memberDTO){
        boolean result = memberService.IDCheck(memberDTO.getMemberEmail());
        if (result) {

            return  new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @Transactional
    @GetMapping("")
    public String findAll(@PageableDefault(page=1)Pageable pageable,
                          @RequestParam(value="type", required=false, defaultValue = "")String type,
                          @RequestParam(value="q", required = false, defaultValue = "")String q,
                          Model model){
        Page<MemberDTO> memberDTOPage = memberService.paging(pageable, type, q);
        if(memberDTOPage.getTotalElements()==0){
            model.addAttribute("memberList", null);
        }else{
            model.addAttribute("memberList", memberDTOPage);
        }
        int blockLimit=3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < memberDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : memberDTOPage.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "/memberPages/memberList";
    }
    @GetMapping("/login")
    public String loginForm(@RequestParam(value="redirectURI", defaultValue = "/member/myPage")String redirectURI,
                           Model model){
        model.addAttribute("redirectURI", redirectURI);
        return "/memberPages/memberLogin";
    }
    @Transactional
    @PostMapping("/login")
        public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session,
                            @RequestParam("redirectURI")String redirectURI){
        boolean memberLoginResult = memberService.login(memberDTO);
        if(memberLoginResult){
            session.setAttribute("loginId", memberDTO.getMemberId());
            System.out.println("loginId =" +memberDTO.getMemberId());
            return "redirect:"+redirectURI;
        }else{
            return "/memberPages/memberLogin";
        }
    }
    @PostMapping("/login/axios")
    public ResponseEntity loginAxios(@RequestBody MemberDTO memberDTO, HttpSession session){
        memberService.loginAxios(memberDTO);
        session.setAttribute("loginId", memberDTO.getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginId");
        return "redirect:/";
    }
    @GetMapping("/myPage")
    public String myPage(){
        return "/memberPages/memberMain";
    }
    @GetMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable Long id){
        MemberDTO memberDTO= memberService.findById(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "/memberPages/memberDetail";
    }
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String loginId = (String)session.getAttribute("loginId");
        System.out.println("loginId = " + loginId);
        MemberDTO memberDTO = memberService.findByMemberId(loginId);
        model.addAttribute("member", memberDTO);
        return "/memberPages/memberUpdate";
    }
    @PostMapping("/update")
    public String update(MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "/memberPages/memberMain";
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
