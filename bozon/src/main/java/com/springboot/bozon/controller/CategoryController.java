package com.springboot.bozon.controller;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.User;
import com.springboot.bozon.service.impl.CategoryServiceImpl;
import com.springboot.bozon.service.impl.PostServiceImpl;
import com.springboot.bozon.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class CategoryController {


    private final PostServiceImpl postService;
    private final CategoryServiceImpl categoryService;
    private final UserServiceImpl userService;

    public CategoryController(PostServiceImpl postService, CategoryServiceImpl categoryService, UserServiceImpl userService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/categories")
    public String getCategories(Model model){
        List<Category>categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String getCategory(@PathVariable("id") long id,
                              @AuthenticationPrincipal UserDetails currentUser,
                              Model model){
        User user = userService.findByUsername(currentUser.getUsername());
        List<Post>posts = postService.findByCategory(id);
        Category category = categoryService.findById(id);
        model.addAttribute("currentUser", user);
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("posts", posts);
        return "post-list";
    }

    @GetMapping("/create_category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createCategory(Model model){
        model.addAttribute("categoryForm", new Category());
        return "create_category";
    }

    @PostMapping("/create_category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCategory(@ModelAttribute("categoryForm") Category categoryForm,  Model model){
        if(!categoryService.save(categoryForm)){
            model.addAttribute("nameError", "Категория с таким именем уже существует");
            return "create_category";
        }
        return "redirect:/categories";
    }
}
