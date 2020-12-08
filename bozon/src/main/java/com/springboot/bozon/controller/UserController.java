package com.springboot.bozon.controller;

import com.springboot.bozon.BozonApplication;
import com.springboot.bozon.model.User;
import com.springboot.bozon.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @author mialyshev
 */

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private static final Logger logger = LoggerFactory.getLogger(BozonApplication.class);

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/userpage/{id}")
    public String getUser(@AuthenticationPrincipal UserDetails currentUser,
                          @PathVariable("id") long id,
                          Model model) {
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        User userById = userService.findById(id);
        User userByUsername = userService.findByUsername(currentUser.getUsername());

        if (!isAdmin && userByUsername.getId() != id){
            model.addAttribute("accessError", "Вы не можете просматривать данные другого пользователя");
            model.addAttribute("user", userByUsername);
            return "home";
        }
        model.addAttribute("userForm", userById);
        return "userpage";
    }

    @GetMapping("/useredit/{id}")
    public String editUser(@AuthenticationPrincipal UserDetails currentUser,
                           @PathVariable("id") long id,
                           Model model) {

        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        User userById = userService.findById(id);
        User userByUsername = userService.findByUsername(currentUser.getUsername());

        if (!isAdmin && userByUsername.getId() != id){
            model.addAttribute("accessError", "Вы не можете редактировать данные другого пользователя");
            model.addAttribute("userForm", userByUsername);
            return "userpage";
        }

        User user = userService.findByUsername(currentUser.getUsername());
        model.addAttribute("userForm", user);
        return "useredit";
    }

    @PostMapping("/useredit/{id}")
    public String updateUser(@AuthenticationPrincipal UserDetails currentUser,
                             @ModelAttribute("userForm") @Valid User userForm,
                             BindingResult bindingResult,
                             @PathVariable("id") long id,
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
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        User userById = userService.findById(id);
        User userByUsername = userService.findByUsername(currentUser.getUsername());

        if (!isAdmin && userByUsername.getId() != id){
            model.addAttribute("accessError", "Вы не можете редактировать данные другого пользователя");
            return "useredit";
        }


        if (!userById.getEmail().equals(userForm.getEmail())) {
            if (userService.isPresentByEmail(userForm.getEmail())) {
                model.addAttribute("emailError", "Пользователь с таким email уже существует");
                return "useredit";
            }
        }

        if (!userById.getPhoneNumber().equals(userForm.getPhoneNumber())) {
            if (userService.isPresentByPhoneNumber(userForm.getPhoneNumber())) {
                model.addAttribute("phoneError", "Пользователь с таким номером телефона уже существует");
                return "useredit";
            }
        }
        userService.updateUserInfo(userForm, id);
        return "redirect:/userpage/" + id;
    }

}
