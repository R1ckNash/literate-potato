package com.springboot.bozon.controller;

import com.springboot.bozon.model.User;
import com.springboot.bozon.service.impl.UserServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mialyshev
 */

@Controller
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/userpage")
    public String getUser(@AuthenticationPrincipal UserDetails currentUser,
                          Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        model.addAttribute("userForm", user);
        return "userpage";
    }

    @GetMapping("/useredit")
    public String editUser(@AuthenticationPrincipal UserDetails currentUser,
                           Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        model.addAttribute("userForm", user);
        return "useredit";
    }

    @PostMapping("/useredit")
    public String updateUser(@AuthenticationPrincipal UserDetails currentUser,
                             @ModelAttribute("userForm") @Valid User userForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                if (error.getField().equals("firstName")){
                    return "useredit";
                }
                if (error.getField().equals("lastName")){
                    return "useredit";
                }
                if (error.getField().equals("email")){
                    return "useredit";
                }
                if (error.getField().equals("phoneNumber")){
                    return "useredit";
                }
            }
        }
        User user = userService.findByUsername(currentUser.getUsername());
        if (!user.getEmail().equals(userForm.getEmail())) {
            if (!userService.findByEmail(userForm.getEmail())) {
                model.addAttribute("emailError", "Пользователь с таким email уже существует");
                return "useredit";
            }
        }

        if (!user.getPhoneNumber().equals(userForm.getPhoneNumber())) {
            if (!userService.findByPhoneNumber(userForm.getPhoneNumber())) {
                model.addAttribute("phoneError", "Пользователь с таким номером телефона уже существует");
                return "useredit";
            }
        }
        userService.updateUserInfo(userForm, user.getId());
        return "redirect:/userpage";
    }

}
