package com.icia.hexagon.Config.Security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String memberId = request.getParameter("memberId");
        request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", memberId);
        String redirectUrl = "/member/login/";
        response.sendRedirect(redirectUrl);
    }
}
