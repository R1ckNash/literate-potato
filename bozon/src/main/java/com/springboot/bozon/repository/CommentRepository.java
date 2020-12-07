package com.springboot.bozon.repository;

import com.springboot.bozon.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mialyshev
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
