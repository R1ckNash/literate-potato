package com.springboot.bozon.controller;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.PostService;
import com.springboot.bozon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author ricknash
 */
@Controller
public class HomePageController {

    private final UserService userService;
    private final PostService postService;

    private final UserRepository userRepository;

    @Autowired
    public HomePageController(UserService userService, PostService postService, UserRepository userRepository) {
        this.userService = userService;
        this.postService = postService;
        this.userRepository = userRepository;
    }

    @GetMapping("/post")
    public String findAll(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post-list";
    }

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

}
