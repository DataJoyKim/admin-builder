package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.security.AuthenticatedUser;
import com.datajoy.admin_builder.security.GrantedAuthority;
import com.datajoy.admin_builder.view.domain.Menu;
import com.datajoy.admin_builder.view.domain.ViewObject;
import com.datajoy.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ViewObjectService {
    private final ViewObjectRepository viewObjectRepository;
    private final MenuRepository menuRepository;
    private final MenuAuthorityRepository menuAuthorityRepository;

    public ViewObject getViewObject(String objectCd) {
        Optional<ViewObject> optionalViewObject = viewObjectRepository.findByObjectCd(objectCd);
        if(optionalViewObject.isEmpty()) {
            return null;
        }

        return optionalViewObject.get();
    }

    public void validateAuthorization(AuthenticatedUser user, ViewObject accessViewObject) throws BusinessException {
        List<GrantedAuthority> grantedAuthorityList = user.getGrantedAuthorities();
        if(grantedAuthorityList.isEmpty()) {
            throw new BusinessException(MenuErrorMessage.NOT_HAS_AUTHORITIES);
        }

        List<Menu> menuList = menuRepository.findByViewObject(accessViewObject);

        // 상위 메뉴 가져오기
        List<Menu> rootMenuList = new ArrayList<>();
        for(Menu menu : menuList) {
            rootMenuList.add(menu.getRootMenu());
        }

        // ROOT 메뉴에 매핑된 권한 가져오기
        Map<String, MenuAuthority> menuAuthorityMap = new HashMap<>();
        for(Menu rootMenu : rootMenuList) {
            List<MenuAuthority> menuAuthorities = menuAuthorityRepository.findByMenu(rootMenu.getRootMenu());

            for(MenuAuthority menuAuthority : menuAuthorities) {
                menuAuthorityMap.put(menuAuthority.getAuthorityCode(), menuAuthority);
            }
        }

        if(menuAuthorityMap.isEmpty()) {
            throw new BusinessException(MenuErrorMessage.NOT_SETTING_AUTHORITY);
        }

        // 내가 가진 권한 검증
        boolean hasAuthority = false;
        for(GrantedAuthority grantedAuthority : grantedAuthorityList) {
            if(menuAuthorityMap.containsKey(grantedAuthority.getRole())) {
                hasAuthority = true;
                break;
            }
        }

        if(!hasAuthority) {
            throw new BusinessException(MenuErrorMessage.PERMISSION_DENIED);
        }
    }
}
