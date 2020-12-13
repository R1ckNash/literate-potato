package com.springboot.bozon.service;

import com.springboot.bozon.model.Comment;
import com.springboot.bozon.model.User;

import java.util.List;

/**
 * @author mialyshev
 */

public interface CommentService {
    Comment findById(Long id);

    List<Comment> findAll();

    void save(User evaluator_user, User rated_user, Comment comment);

    boolean deleteById(Long id);
}
