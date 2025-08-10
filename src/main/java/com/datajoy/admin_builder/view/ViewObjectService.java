package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.ViewObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewObjectService {
    private final ViewObjectRepository viewObjectRepository;

    public ViewObject getViewObject(RequestMessage message) {
        Optional<ViewObject> optionalViewObject = viewObjectRepository.findByObjectCd(message.getBody().getObjectCd());
        if(optionalViewObject.isEmpty()) {
            return null;
        }

        return optionalViewObject.get();
    }

}
