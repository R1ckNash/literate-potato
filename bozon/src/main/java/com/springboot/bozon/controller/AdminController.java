package com.springboot.bozon.controller;

import com.springboot.bozon.model.Role;
import com.springboot.bozon.model.User;
import com.springboot.bozon.service.impl.RoleServiceImpl;
import com.springboot.bozon.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author mialyshev
 */

@Controller
@RequestMapping("/admin_panel")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public String getAdminPanel(){
        return "admin/admin_panel";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get_all_users")
    public String getUsers(Model model){
        List<User> users = userService.getAll();
        List<Role> roles = roleService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update_user/{id}")
    public String updateUserInfo(@AuthenticationPrincipal UserDetails currentUser,
                                 @PathVariable("id") long id,
                                 Model model){
        User userFromDB = userService.findById(id);
        if (!userFromDB.getUsername().equals(currentUser.getUsername())) {
            List<Role> roles = roleService.getAll();
            model.addAttribute("roles", roles);
        }
        model.addAttribute("userForm", userFromDB);
        return "useredit";
    }


}
