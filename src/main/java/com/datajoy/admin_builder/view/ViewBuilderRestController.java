package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.security.AuthService;
import com.datajoy.admin_builder.security.AuthenticatedUser;
import com.datajoy.admin_builder.security.SecurityBusinessException;
import com.datajoy.admin_builder.security.TokenUtil;
import com.datajoy.admin_builder.view.domain.Layout;
import com.datajoy.admin_builder.view.domain.ViewObject;
import com.datajoy.admin_builder.view.domain.ViewObjectContent;
import com.datajoy.admin_builder.view.dto.MenuDto;
import com.datajoy.admin_builder.view.dto.ProfileDto;
import com.datajoy.core.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ViewBuilderRestController {
    @Autowired
    MenuService menuService;
    @Autowired
    LayoutService layoutService;
    @Autowired
    AuthService authService;
    @Autowired
    ViewObjectService viewObjectService;

    @GetMapping("/api/menu/tree")
    public ResponseEntity<?> getMenu(@RequestParam("parentMenuCd") String parentMenuCd) {
        List<MenuDto> menuList = menuService.getMenuTree(parentMenuCd);

        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }
    @GetMapping("/api/menu/root")
    public ResponseEntity<?> getMenuRoot(HttpServletRequest request) {
        AuthenticatedUser user;
        try {
            user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
        }
        catch (SecurityBusinessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<MenuDto> menuList = menuService.getMenuRoot(user);

        return new ResponseEntity<>(menuList, HttpStatus.OK);
    }
    @GetMapping("/api/layout")
    public ResponseEntity<?> getLayout() {
        Layout response = layoutService.getLayout();
        if(response == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/api/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        AuthenticatedUser user;
        try {
            user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
        }
        catch (SecurityBusinessException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ProfileDto.ProfileResponse response = ProfileDto.ProfileResponse.of(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pages/{objectCode}/definition")
    public ResponseEntity<?> getPageDefinition(
            HttpServletRequest request,
            @PathVariable("objectCode") String objectCode
    ) {
        ViewObject viewObject = viewObjectService.getViewObject(objectCode);

        if(Boolean.TRUE.equals(viewObject.getUseAuthValidation())) {
            AuthenticatedUser user;
            try {
                user = authService.validateAuthentication(TokenUtil.resolveAccessToken(request));
            } catch (SecurityBusinessException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if(Boolean.TRUE.equals(viewObject.getUseAuthorityValidation())) {
                try {
                    viewObjectService.validateAuthorization(user, viewObject);
                }
                catch (BusinessException e) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }
        }

        ViewObjectContent viewObjectContent = viewObjectService.getViewObjectContent(objectCode);

        return new ResponseEntity<>(viewObjectContent, HttpStatus.OK);
    }
}
