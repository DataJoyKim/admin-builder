package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.message.ResponseMessage;
import com.datajoy.admin_builder.security.AuthService;
import com.datajoy.admin_builder.security.AuthenticatedUser;
import com.datajoy.admin_builder.security.SecurityBusinessException;
import com.datajoy.admin_builder.security.TokenUtil;
import com.datajoy.admin_builder.view.domain.ViewObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class ViewBuilderController {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ViewObjectService viewObjectService;
    @Autowired
    AuthService authService;
    @GetMapping("")
    public String moveAppIndex() {
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
