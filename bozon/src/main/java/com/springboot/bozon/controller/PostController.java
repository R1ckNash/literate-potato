package com.springboot.bozon.controller;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.impl.CategoryServiceImpl;
import com.springboot.bozon.service.impl.PostServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author mialyshev
 */
@Controller
public class PostController {

    private final UserRepository userRepository;
    private final PostServiceImpl postService;
    private CategoryServiceImpl categoryService;

    public PostController(UserRepository userRepository, PostServiceImpl postService, CategoryServiceImpl categoryService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/posts")
    public String findAll(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) userRepository.findByUsername(currentUser.getUsername());
        model.addAttribute("currentUser", user);
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "post-list";
    }

    @GetMapping("/create_post")
    public String getAllPost(Model model) {
        model.addAttribute("postForm", new Post());
        List<Category>categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "create-post";
    }

    @PostMapping("/create_post")
    public String savePost(@ModelAttribute("postForm")Post postForm,
                           @AuthenticationPrincipal UserDetails currentUser,
                           Model model) {
        postService.save(postForm, currentUser.getUsername());
        return "redirect:/posts";
    }
}
