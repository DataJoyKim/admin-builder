package com.datajoy.admin_builder.console;

import com.datajoy.admin_builder.security.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class ConsoleSecurityFilter implements Filter {
    private final AuthService authService;
    private final SecurityProperties securityProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        AuthenticatedUser user;
        try {
            user = authService.validateAuthentication(TokenUtil.resolveAccessToken((HttpServletRequest) request));
        }
        catch (SecurityBusinessException e) {
            String returnUrl = URLEncoder.encode("/console", StandardCharsets.UTF_8);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/error/error401?returnUrl="+returnUrl);
            //TODO alert 띄우고 로그인페이지 넘어가기
            return;
        }

        if(!GrantedAuthority.hasAuthority(user.getGrantedAuthorities(), securityProperties.getConsoleAccessPermitAuthorityCode())) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/error/error403");
            //TODO 접근불가 페이지 넘어가기
            return;
        }

        chain.doFilter(request, response);
    }
}
