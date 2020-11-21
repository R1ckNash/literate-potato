package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.PostRepository;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ricknash
 */
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post findById(Long id) {
        return postRepository.getOne(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findAllActive() {
        ArrayList<Post>postArrayList = new ArrayList<>();
        List<Post>posts = findAll();
        for(Post post : posts){
            if (post.getStatus() == Status.ACTIVE){
                postArrayList.add(post);
            }
        }
        return postArrayList;
    }

    public boolean save(Post post, String username) {
        User user = userRepository.findByUsername(username);
        post.setSeller(user);
        post.setStatus(Status.ACTIVE);
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
        ArrayList<Post>postArrayList = new ArrayList<>();

        List<Post> posts = findAllActive();

        for(Post post : posts){
            if (post.getCategory().getId() == categoryId){
                postArrayList.add(post);
            }
        }
        return postArrayList;
    }

    @Override
    public void setStatus(Long postId, Status status) {
        Post post = findById(postId);
        post.setStatus(status);
        postRepository.save(post);
    }

    @Override
    public List<Post> findByUser(Long userId) {
        List<Post>posts = findAllActive();
        ArrayList<Post> userPosts = new ArrayList<>();
        for(Post post : posts){
            if(post.getSeller().getId() == userId){
                userPosts.add(post);
            }
        }
        return userPosts;
    }
}
