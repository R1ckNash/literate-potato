package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.PostRepository;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.PostService;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author mialyshev
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post findById(Long id) {
        return postRepository.getOne(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findAllActive() {
        List<Post> posts = findAll();
        return StreamEx.of(posts)
                .filter(post -> post.getStatus() == Status.ACTIVE)
                .toList();
    }

    public boolean save(Post post, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            post.setSeller(user.get());
            post.setStatus(Status.ACTIVE);
        } else {
            // TODO: определить поведение, если пользователь не найден
            throw new RuntimeException("fatal error???");
        }
        postRepository.save(post);
        return true;
    }

    public boolean deleteById(Long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Post> findByCategory(long categoryId) {
        List<Post> posts = findAllActive();
        return StreamEx.of(posts)
                .filter(post -> post.getCategory().getId() == categoryId)
                .toList();
    }

    @Override
    @Transactional
    public void setStatus(Long postId, Status status) {
        Post post = findById(postId);
        post.setStatus(status);
    }

    @Override
    public List<Post> findByUser(Long userId) {
        List<Post> posts = findAllActive();
        return StreamEx.of(posts)
                .filter(post -> post.getSeller().getId().equals(userId))
                .toList();
    }
}
