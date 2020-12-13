package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Comment;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.CommentRepository;
import com.springboot.bozon.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mialyshev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void save(User evaluator_user, User rated_user, Comment comment) {
        comment.setEvaluator_user(evaluator_user);
        comment.setRated_user(rated_user);
        comment.setStatus(Status.ACTIVE);
        commentRepository.save(comment);
    }

    @Override
    public boolean deleteById(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
