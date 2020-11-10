package com.springboot.bozon.controller;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.impl.CategoryServiceImpl;
import com.springboot.bozon.service.impl.PostServiceImpl;
import com.springboot.bozon.service.impl.SaleServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author mialyshev
 */
@Controller
public class PostController {

    private final UserRepository userRepository;
    private final PostServiceImpl postService;
    private final SaleServiceImpl saleService;
    private CategoryServiceImpl categoryService;

    public PostController(UserRepository userRepository, PostServiceImpl postService, SaleServiceImpl saleService, CategoryServiceImpl categoryService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.saleService = saleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/posts")
    public String findAll(@AuthenticationPrincipal UserDetails currentUser,
                          Model model) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        List<Post> posts = postService.findAllActive();
        List<Category>categories = categoryService.findAll();
        model.addAttribute("currentUser", user);
        model.addAttribute("categories", categories);
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
                           @AuthenticationPrincipal UserDetails currentUser) {
        postService.save(postForm, currentUser.getUsername());
        return "redirect:/posts";
    }

    @GetMapping("/buypost/{id}")
    public String buyPost(@PathVariable("id") long id,
                          @AuthenticationPrincipal UserDetails currentUser){
        User user = userRepository.findByUsername(currentUser.getUsername());
        Post post = postService.findById(id);
        saleService.save(post, user);
        postService.setStatus(id, Status.NOT_ACTIVE);
        return "redirect:/posts";
    }

    @GetMapping("/userpost")
    public String userPost(@AuthenticationPrincipal UserDetails currentUser,
                          Model model){
        User user = userRepository.findByUsername(currentUser.getUsername());
        List<Post> posts = postService.findByUser(user.getId());
        List<Category>categories = categoryService.findAll();
        model.addAttribute("currentUser", user);
        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);
        return "post-list";
    }

    @GetMapping("/closepost/{id}")
    public String closePost(@PathVariable("id") long id,
                          @AuthenticationPrincipal UserDetails currentUser){
        postService.setStatus(id, Status.NOT_ACTIVE);
        return "redirect:/posts";
    }
}
