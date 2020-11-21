package com.springboot.bozon.controller;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.impl.CategoryServiceImpl;
import com.springboot.bozon.service.impl.PostServiceImpl;
import com.springboot.bozon.service.impl.SaleServiceImpl;
import com.springboot.bozon.service.impl.UserServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;
    private final SaleServiceImpl saleService;
    private CategoryServiceImpl categoryService;

    public PostController(UserRepository userRepository, UserServiceImpl userService, PostServiceImpl postService, SaleServiceImpl saleService, CategoryServiceImpl categoryService) {
        this.userService = userService;
        this.postService = postService;
        this.saleService = saleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/posts")
    public String findAll(Model model) {
        List<Post> posts = postService.findAllActive();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);
        return "post-list";
    }

    @GetMapping("/create_post")
    public String getAllPost(Model model) {
        model.addAttribute("postForm", new Post());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "create-post";
    }

    @PostMapping("/create_post")
    public String savePost(Model model, @ModelAttribute("postForm") @Valid Post postForm,
                           BindingResult bindingResult,
                           @AuthenticationPrincipal UserDetails currentUser) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            return "create-post"; //баг , не выводятся категории
        }
        postService.save(postForm, currentUser.getUsername());
        return "redirect:/posts";
    }

    @GetMapping("/buypost/{id}")
    public String buyPost(@PathVariable("id") long id,
                          @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        Post post = postService.findById(id);
        saleService.save(post, user);
        postService.setStatus(id, Status.NOT_ACTIVE);
        return "redirect:/posts";
    }

    @GetMapping("/userpost")
    public String userPost(@AuthenticationPrincipal UserDetails currentUser,
                           Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        List<Post> posts = postService.findByUser(user.getId());
        model.addAttribute("posts", posts);
        return "post-list";
    }

    @GetMapping("/closepost/{id}")
    public String closePost(@PathVariable("id") long id) {
        postService.setStatus(id, Status.NOT_ACTIVE);
        return "redirect:/posts";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") long id,
                          @AuthenticationPrincipal UserDetails currentUser,
                          Model model) {
        User user = userService.findByUsername(currentUser.getUsername());
        Post post = postService.findById(id);
        model.addAttribute("currentUser", user);
        model.addAttribute("postForm", post);
        return "post";
    }
}
