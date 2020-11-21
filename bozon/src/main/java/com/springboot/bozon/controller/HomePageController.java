package com.springboot.bozon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ricknash
 */
@Controller
public class HomePageController {

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

}
