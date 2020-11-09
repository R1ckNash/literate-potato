package com.springboot.bozon.controller;

import com.springboot.bozon.model.User;
import com.springboot.bozon.service.UserDetailsServiceImpl;
import com.springboot.bozon.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author mialyshev
 */

@Controller
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    private final UserServiceImpl userService;

    public UserController(UserDetailsServiceImpl userDetailsService, UserServiceImpl userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm")User userForm, Model model) {
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }

        if(!userService.findByEmail(userForm.getEmail())){
            model.addAttribute("emailError", "Пользователь с таким email уже существует");
            return "registration";
        }

        if(!userService.findByPhoneNumber(userForm.getPhoneNumber())){
            model.addAttribute("phoneError", "Пользователь с таким номером телефона уже существует");
            return "registration";
        }

        if (!userService.save(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/";
    }

}