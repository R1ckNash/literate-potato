package com.springboot.bozon.repository;

import com.springboot.bozon.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mialyshev
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByName(String name);
}
