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
}
