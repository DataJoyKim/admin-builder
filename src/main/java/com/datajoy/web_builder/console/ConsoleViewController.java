package com.datajoy.web_builder.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsoleViewController {

    @GetMapping("/console")
    public String moveIndex() {
        return "index";
    }
}
