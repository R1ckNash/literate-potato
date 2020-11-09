package com.springboot.bozon.service;

import com.springboot.bozon.model.Post;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author mialyshev
 */
public interface PostService {
    Post findById(Long id);

    List<Post> findAll();

    boolean save(Post post, String username);

    boolean deleteById(Long id);
}
