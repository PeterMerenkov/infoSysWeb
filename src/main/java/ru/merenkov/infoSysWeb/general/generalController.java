package ru.merenkov.infoSysWeb.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class generalController {

    @GetMapping
    public String getIndexPage() {
        return "index";
    }
}
