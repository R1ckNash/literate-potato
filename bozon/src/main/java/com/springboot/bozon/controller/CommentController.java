package com.springboot.bozon.controller;

import com.springboot.bozon.model.Comment;
import com.springboot.bozon.model.User;
import com.springboot.bozon.service.impl.CommentServiceImpl;
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
public class CommentController {

    private final UserServiceImpl userService;
    private final CommentServiceImpl commentService;

    public CommentController(UserServiceImpl userService, CommentServiceImpl commentService) {
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/comments/{id}")
    public String getUserComments(@PathVariable("id") long id,
                                  Model model,
                                  @AuthenticationPrincipal UserDetails currentUser){
        User userFromDB = userService.findById(id);
        User userByUsername = userService.findByUsername(currentUser.getUsername());
        if(userFromDB == null){
            model.addAttribute("userError", "Пользователь с id: " + id + " не найден");
            return "comments";
        }
        int rating = userService.getAVGRating(id);
        List<Comment> comments = List.copyOf(userFromDB.getRating());
        model.addAttribute("user", userFromDB);
        model.addAttribute("currentUser", userByUsername);
        model.addAttribute("comments", comments);
        model.addAttribute("rating", rating);
        model.addAttribute("commentForm", new Comment());
        return "comments";
    }


    @PostMapping("/comments/{id}")
    public String addUserComments(@AuthenticationPrincipal UserDetails currentUser,
                                  @ModelAttribute("commentForm") @Valid Comment commentForm,
                                  BindingResult bindingResult,
                                  @PathVariable("id") long id,
                                  Model model){
        if (bindingResult.hasErrors()) {
            return "comments";
        }
        User userByUsername = userService.findByUsername(currentUser.getUsername());
        User userFromDB = userService.findById(id);
        if (userByUsername == userFromDB){
            model.addAttribute("commentError", "Вы не можете оставлять комментарии о себе");
            return "comments";
        }
        commentService.save(userByUsername, userFromDB, commentForm);
        return "redirect:/comments/" + userFromDB.getId();
    }
}
