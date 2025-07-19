package com.datajoy.admin_builder.console;

import com.datajoy.admin_builder.security.*;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

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
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(securityProperties.getLoginPath());
            return;
        }

        if(!GrantedAuthority.hasAuthority(user.getGrantedAuthorities(), securityProperties.getConsoleAccessPermitAuthorityCode())) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(securityProperties.getLoginPath());
            return;
        }

        chain.doFilter(request, response);
    }
}
