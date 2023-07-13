package com.icia.hexagon.Config.Security;

import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginIdPwValidator implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpServletRequest request;

    // 생성자 주입을 통해 MemberRepository 및 HttpServletRequest 의존성 주입
    public LoginIdPwValidator(MemberRepository memberRepository, HttpServletRequest request) {
        this.memberRepository = memberRepository;
        this.request = request;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        // 사용자 이메일로 MemberEntity 조회
        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberId);

        // 조회된 MemberEntity가 없을 경우 UsernameNotFoundException 발생
        if (memberId == null || !byMemberId.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        // 조회된 MemberEntity에서 비밀번호 추출
        String pw = byMemberId.get().getMemberPassword();

        // 사용자의 권한 설정
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(memberId)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // UserDetails 객체 생성하여 반환
        UserDetails user = User.builder()
                .username(memberId) // 사용자 이메일을 username으로 설정
                .password(pw) // 조회된 비밀번호를 설정
                .authorities(authorities) // 권한 설정
                .build();

        // 인증에 성공한 경우 세션에 memberId 값을 저장
        HttpSession session = request.getSession();
        session.setAttribute("loginId", memberId);

        return user;
    }
}
