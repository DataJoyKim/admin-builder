package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.security.AuthenticatedUser;
import com.datajoy.admin_builder.view.domain.ViewObject;
import com.datajoy.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewObjectService {
    private final ViewObjectRepository viewObjectRepository;

    public ViewObject getViewObject(String objectCd) {
        Optional<ViewObject> optionalViewObject = viewObjectRepository.findByObjectCd(objectCd);
        if(optionalViewObject.isEmpty()) {
            return null;
        }

        return optionalViewObject.get();
    }

    public void validateAuthorization(AuthenticatedUser user, ViewObject accessViewObject) throws BusinessException {
        // 상위 ROOT 오브젝트 가져오기
        // ROOT 오브젝트에 매핑된 권한 가져오기
        // 내가 가진 권한 검증
    }
}
