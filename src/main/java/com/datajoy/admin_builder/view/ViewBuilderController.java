package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.security.*;
import com.datajoy.admin_builder.view.domain.ViewObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping
public class ViewBuilderController {
    @Autowired
    ViewObjectService viewObjectService;
    @Autowired
    AuthService authService;
    @Autowired
    SecurityProperties securityProperties;

    @GetMapping("")
    public String moveAppIndex(HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        AuthenticatedUser user = null;
        try {
            user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
        }
        catch (SecurityBusinessException e) {
            httpResponse.sendRedirect(securityProperties.getLoginPath());
        }

        return "/pages/index";
    }
    @GetMapping("/pages/{objectCd}")
    public String moveAppPages(
            HttpServletRequest request,
            @PathVariable("objectCd") String objectCd
    ) {
        AuthenticatedUser user = null;
        try {
            user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
        }
        catch (SecurityBusinessException e) {
            return "/error/login-error";
        }

        ViewObject viewObject = viewObjectService.getViewObject(objectCd);

        return "/pages" + viewObject.getPath();
    }
}
