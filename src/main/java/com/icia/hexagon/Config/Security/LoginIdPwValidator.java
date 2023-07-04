package com.icia.hexagon.Config.Security;

import com.icia.hexagon.Entity.MemberEntity;
import com.icia.hexagon.Repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public UserDetails loadUserByUsername(String insertedId) throws UsernameNotFoundException {
        // 사용자 이메일로 MemberEntity 조회
        Optional<MemberEntity> memberId = memberRepository.findByMemberId(insertedId);

        // 조회된 MemberEntity가 없을 경우 UsernameNotFoundException 발생
        if (memberId == null || !memberId.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        // 조회된 MemberEntity에서 비밀번호와 권한 정보 추출
        String pw = memberId.get().getMemberPassword();
        String roles = memberId.get().getRoles();

        // UserDetails 객체 생성하여 반환
        UserDetails user = User.builder()
                .username(insertedId) // 사용자 이메일을 username으로 설정
                .password(pw) // 조회된 비밀번호를 설정
                .roles(roles) // 조회된 권한 정보를 설정
                .build();

        // 인증에 성공한 경우 세션에 memberId 값을 저장
        HttpSession session = request.getSession();
        session.setAttribute("loginEmail", insertedId);

        return user;
    }
}
