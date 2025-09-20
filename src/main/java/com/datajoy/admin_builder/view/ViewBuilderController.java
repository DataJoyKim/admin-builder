package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.security.*;
import com.datajoy.admin_builder.view.domain.Layout;
import com.datajoy.admin_builder.view.domain.ViewObject;
import com.datajoy.core.exception.BusinessException;
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
    LayoutService layoutService;
    @Autowired
    ViewObjectService viewObjectService;
    @Autowired
    AuthService authService;
    @Autowired
    SecurityProperties securityProperties;

    @GetMapping("")
    public String moveAppIndex(HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        Layout layout = layoutService.getLayout();

        if(Boolean.TRUE.equals(layout.getUseAuthValidation())) {
            AuthenticatedUser user = null;
            try {
                user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
            }
            catch (SecurityBusinessException e) {
                httpResponse.sendRedirect(securityProperties.getLoginPath()+"?returnUrl=/");
            }
        }

        return "/pages/index";
    }
    @GetMapping("/pages/{objectCd}")
    public String moveAppPages(
            HttpServletRequest request,
            @PathVariable("objectCd") String objectCd
    ) {
        ViewObject viewObject = viewObjectService.getViewObject(objectCd);

        if(Boolean.TRUE.equals(viewObject.getUseAuthValidation())) {
            AuthenticatedUser user;
            try {
                user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
            } catch (SecurityBusinessException e) {
                return "/error/error401";
            }

            if(Boolean.TRUE.equals(viewObject.getUseAuthorityValidation())) {
                try {
                    viewObjectService.validateAuthorization(user, viewObject);
                }
                catch (BusinessException e) {
                    return "/error/error403";
                }
            }
        }

        return "/pages" + viewObject.getPath();
    }
}
