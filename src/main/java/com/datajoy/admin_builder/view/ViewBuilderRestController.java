package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.security.AuthService;
import com.datajoy.admin_builder.security.AuthenticatedUser;
import com.datajoy.admin_builder.security.SecurityBusinessException;
import com.datajoy.admin_builder.security.TokenUtil;
import com.datajoy.admin_builder.view.domain.Layout;
import com.datajoy.admin_builder.view.dto.MenuDto;
import com.datajoy.admin_builder.view.dto.ProfileDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
