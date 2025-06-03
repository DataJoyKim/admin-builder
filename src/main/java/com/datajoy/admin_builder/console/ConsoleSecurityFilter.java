package com.datajoy.admin_builder.console;

import com.datajoy.admin_builder.apibuilder.security.AuthService;
import com.datajoy.admin_builder.apibuilder.security.SecurityBusinessException;
import com.datajoy.admin_builder.apibuilder.security.TokenUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ConsoleSecurityFilter implements Filter {
    @Autowired
    private AuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            authService.validateAuthentication(TokenUtil.resolveToken((HttpServletRequest) request));
        } catch (SecurityBusinessException e) {
            throw new RuntimeException(e);
        }

        chain.doFilter(request, response);
    }
}
