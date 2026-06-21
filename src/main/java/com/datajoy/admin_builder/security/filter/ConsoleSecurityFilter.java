package com.datajoy.admin_builder.security.filter;

import com.datajoy.admin_builder.security.domain.AuthenticatedUser;
import com.datajoy.admin_builder.security.domain.GrantedAuthority;
import com.datajoy.admin_builder.security.config.SecurityProperties;
import com.datajoy.admin_builder.security.exception.SecurityBusinessException;
import com.datajoy.admin_builder.security.service.AuthService;
import com.datajoy.admin_builder.security.token.TokenCookie;
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
            user = authService.authentication(TokenCookie.resolveAccessToken((HttpServletRequest) request));
        }
        catch (SecurityBusinessException e) {
            String returnUrl = URLEncoder.encode("/console", StandardCharsets.UTF_8);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/error/error401?returnUrl="+returnUrl);
            return;
        }

        if(!GrantedAuthority.hasAuthority(user.getGrantedAuthorities(), securityProperties.getConsoleAccessPermitAuthorityCode())) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/error/error403");
            return;
        }

        chain.doFilter(request, response);
    }
}
