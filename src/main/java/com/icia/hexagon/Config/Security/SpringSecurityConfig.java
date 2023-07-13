package com.icia.hexagon.Config.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginIdPwValidator loginIdPwValidator;

    // HTTP 요청에 대한 보안 설정을 구성하는 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/member/save", "/member/login", "/member/dup-check", "/css/**", "/images/**", "/game", "/game/detail/**", "/upload/**", "/game/discount", "/game/release", "/member/login/").permitAll()  // 인증(로그인) 없이 접근 허용
                .antMatchers("/member/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/member/update/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/member/delete/**").authenticated()
                .anyRequest().authenticated()  // 나머지 요청은 인증된 사용자만 접근 가능
                .and()
                .formLogin()
                .loginPage("/member/login")  // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/member/login")  // 로그인 처리 URL
                .usernameParameter("memberId")  // 사용자 이름 파라미터명
                .passwordParameter("memberPassword")  // 비밀번호 파라미터명
                .defaultSuccessUrl("/", false)  // 로그인 성공 시 이동할 URL
                .failureHandler(new CustomAuthenticationFailureHandler())  // 커스텀 실패 핸들러 지정
                .permitAll()  // 로그인 페이지에 대한 접근 허용
                .and()
                .logout()
                .logoutSuccessUrl("/")  // 로그아웃 성공 시 이동할 URL
                .permitAll();  // 로그아웃에 대한 접근 허용
    }

    // Web 보안을 구성하기 위한 설정을 추가하는 메소드
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/js/**", "/static/css/**", "/static/img/**", "/static/frontend/**");  // 정적 자원에 대한 보안 설정 무시
    }

    // 인증을 위한 사용자 세부 서비스를 구성하는 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // 비밀번호 인코더를 빈으로 등록하는 메소드
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 프로바이더를 빈으로 등록하는 메소드
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(loginIdPwValidator);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
