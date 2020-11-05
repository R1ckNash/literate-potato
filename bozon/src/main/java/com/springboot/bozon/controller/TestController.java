package com.springboot.bozon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ricknash
 */

@Controller
public class TestController {

    @GetMapping("/goodbye")
    public String sayHello() {
        return "goodbye";
    }
}
