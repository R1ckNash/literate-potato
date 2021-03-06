package com.springboot.bozon.service;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Status;

import java.util.List;

/**
 * @author mialyshev
 */
public interface PostService {
    Post findById(Long id);

    List<Post> findAll();

    List<Post> findAllActive();

    boolean save(Post post, String username);

    boolean deleteById(Long id);

    List<Post> findByCategory(long categoryId);

    void setStatus(Long postId, Status status);

    List<Post> findByUser(Long userId);
}
