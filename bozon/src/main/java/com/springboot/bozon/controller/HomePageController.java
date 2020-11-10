package com.springboot.bozon.controller;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.impl.PostServiceImpl;
import com.springboot.bozon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
