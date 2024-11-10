package com.datajoy.admin_builder.console;

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
        return "index";
    }

    @GetMapping("/{path}")
    public String moveDataSource(Model model, @PathVariable(name = "path") String path) {
        model.addAttribute("contentPath","/contents/"+path);
        return "contents/content";
    }
}
