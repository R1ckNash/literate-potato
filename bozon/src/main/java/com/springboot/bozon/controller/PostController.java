package com.springboot.bozon.controller;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.impl.PostServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author mialyshev
 */
@Controller
public class PostController {

    private final UserRepository userRepository;
    private final PostServiceImpl postService;

    public PostController(UserRepository userRepository, PostServiceImpl postService) {
        this.userRepository = userRepository;
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String findAll(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) userRepository.findByUsername(currentUser.getUsername());
        model.addAttribute("currentUser", user);
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "post-list";
    }

    @GetMapping("/createpost")
    public String createPost(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        model.addAttribute("postForm", new Post());
        return "create-post";
    }
}
