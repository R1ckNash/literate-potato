package com.springboot.bozon.controller;

import com.springboot.bozon.model.User;
import com.springboot.bozon.service.impl.UserServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mialyshev
 */
@Controller
public class HomePageController {

    private final UserServiceImpl userService;

    public HomePageController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome(@AuthenticationPrincipal UserDetails currentUser,
                           Model model) {
        if(currentUser != null){
            User userFromDB = userService.findByUsername(currentUser.getUsername());
            model.addAttribute("user", userFromDB);
        }
        return "home";
    }

}
