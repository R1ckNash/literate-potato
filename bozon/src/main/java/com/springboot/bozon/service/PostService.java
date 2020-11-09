package com.springboot.bozon.service;

import com.springboot.bozon.model.Post;

import java.util.List;

/**
 * @author mialyshev
 */
public interface PostService {
    Post findById(Long id);

    List<Post> findAll();

    boolean save(Post post);

    boolean deleteById(Long id);
}
