package com.datajoy.admin_builder.console;

import com.datajoy.admin_builder.apibuilder.security.AuthService;
import com.datajoy.admin_builder.apibuilder.security.SecurityBusinessException;
import com.datajoy.admin_builder.apibuilder.security.TokenUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class ConsoleSecurityFilter implements Filter {
    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            authService.validateAuthentication(TokenUtil.resolveToken((HttpServletRequest) request));

            chain.doFilter(request, response);
        }
        catch (SecurityBusinessException e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/login");
        }
    }
}
