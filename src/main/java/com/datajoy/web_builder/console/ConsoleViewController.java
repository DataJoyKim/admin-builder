package com.datajoy.web_builder.console;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/console")
public class ConsoleViewController {

    @GetMapping("")
    public String moveIndex() {
        return "index";
    }

    @GetMapping("/dataSource")
    public String moveDataSource(Model model) {
        model.addAttribute("contentPath","/contents/database/datasource");
        return "contents/content";
    }

    @GetMapping("/entity")
    public String moveEntity(Model model) {
        model.addAttribute("contentPath","/contents/api/entity");
        return "contents/content";
    }

    @GetMapping("/function")
    public String moveFunction(Model model) {
        model.addAttribute("contentPath","/contents/api/function");
        return "contents/content";
    }
}
