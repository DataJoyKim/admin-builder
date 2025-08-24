package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.ViewObject;
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

}
