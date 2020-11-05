package com.springboot.bozon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ricknash
 */
@Controller
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "hello";
    }
}
