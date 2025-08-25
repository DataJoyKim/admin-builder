package com.datajoy.admin_builder.console.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/console")
public class ConsoleViewController {

    @GetMapping("")
    public String moveIndex() {
        return "console/index";
    }

    @GetMapping("/{path}")
    public String moveDataSource(Model model, @PathVariable(name = "path") String path) {
        model.addAttribute("contentPath","/console/contents/"+path);
        return "console/contents/content";
    }
}
