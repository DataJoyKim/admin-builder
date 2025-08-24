package com.datajoy.admin_builder.view;

import com.datajoy.admin_builder.view.domain.ViewObject;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @GetMapping("")
    public String moveAppIndex() {
        return "/pages/index";
    }
    @GetMapping("/pages/{objectCd}")
    public String moveAppPages(@PathVariable("objectCd") String objectCd) {
        ViewObject viewObject = viewObjectService.getViewObject(objectCd);

        return "/pages" + viewObject.getPath();
    }
}
