package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.repository.PostRepository;
import com.springboot.bozon.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ricknash
 */
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(Long id) {
        return postRepository.getOne(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public boolean save(Post post) {
        Post postDromDB = postRepository.findByName(post.getName());
        if(postDromDB != null){
            return false;
        }
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
}
