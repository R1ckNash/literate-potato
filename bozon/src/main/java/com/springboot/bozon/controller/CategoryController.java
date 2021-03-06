package com.springboot.bozon.controller;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Post;
import com.springboot.bozon.service.impl.CategoryServiceImpl;
import com.springboot.bozon.service.impl.PostServiceImpl;
import com.springboot.bozon.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String getCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String getCategory(@PathVariable("id") long id,
                              Model model) {
        List<Post> posts = postService.findByCategory(id);
        Category category = categoryService.findById(id);
        model.addAttribute("posts", posts);
        return "post-list";
    }

    @GetMapping("/create_category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createCategory(Model model) {
        model.addAttribute("categoryForm", new Category());
        return "create_category";
    }

    @PostMapping("/create_category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveCategory(@ModelAttribute("categoryForm") @Valid Category categoryForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "create_category";
        }

        if (!categoryService.save(categoryForm)) {
            model.addAttribute("nameError", "Категория с таким именем уже существует");
            return "create_category";
        }
        return "redirect:/categories";
    }
}
