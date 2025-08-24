package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.ViewObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping
public class ViewBuilderController {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ViewObjectService viewObjectService;
    @Autowired
    MenuService menuService;
    @GetMapping("")
    public String moveAppIndex() {
        return "/view/index";
    }
    @PostMapping("/pages")
    public String moveAppPages(
            @RequestParam Map<String,String> params
    ) {
        RequestMessage message = RequestMessage.createRequestMessage(objectMapper, params);

        ViewObject viewObject = viewObjectService.getViewObject(message);

        return "/view" + viewObject.getPath();
    }
}
