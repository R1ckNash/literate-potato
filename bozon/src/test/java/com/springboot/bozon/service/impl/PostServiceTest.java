package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.PostRepository;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(PostServiceImpl.class)
class PostServiceTest {

    private static final Post TEST_POST;
    private static final Post SECOND_TEST_POST;
    private static final Long TEST_ID = 1L;

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private PostService postService;

    static {
        TEST_POST = new Post();
        TEST_POST.setId(TEST_ID);
        TEST_POST.setStatus(Status.ACTIVE);
        Category category = new Category();
        category.setId(TEST_ID);
        TEST_POST.setCategory(category);
        User user = new User();
        user.setId(TEST_ID);
        TEST_POST.setSeller(user);

        SECOND_TEST_POST = new Post();
        SECOND_TEST_POST.setId(2L);
        SECOND_TEST_POST.setStatus(Status.NOT_ACTIVE);
    }

    @Test
    void findById() {
        when(postRepository.getOne(TEST_ID)).thenReturn(TEST_POST);
        assertEquals(TEST_POST, postService.findById(TEST_ID));
        assertNull(postService.findById(2L));
    }

    @Test
    void findAll() {
        when(postRepository.findAll()).thenReturn(List.of(TEST_POST, SECOND_TEST_POST));
        assertEquals(List.of(TEST_POST, SECOND_TEST_POST), postService.findAll());
    }

    @Test
    void findAllActive() {
        when(postRepository.findAll()).thenReturn(List.of(TEST_POST, SECOND_TEST_POST));
        assertEquals(List.of(TEST_POST), postService.findAllActive());
    }

    // TODO: написать, как будет определно поведение метода
    @Test
    void save() {
    }

    @Test
    void deleteById() {
        when(postRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_POST));
        assertTrue(postService.deleteById(TEST_ID));
        assertFalse(postService.deleteById(2L));
    }

    @Test
    void findByCategory() {
        when(postRepository.findAll()).thenReturn(List.of(TEST_POST, SECOND_TEST_POST));
        assertEquals(List.of(TEST_POST), postService.findByCategory(TEST_ID));
        assertEquals(Collections.emptyList(), postService.findByCategory(2L));
    }

    @Test
    void setStatus() {
        when(postRepository.getOne(TEST_ID)).thenReturn(TEST_POST);
        Status actualStatus = TEST_POST.getStatus();
        postService.setStatus(TEST_ID, Status.NOT_ACTIVE);
        assertEquals(Status.NOT_ACTIVE, TEST_POST.getStatus());
        // сбрасываем статус в прежнее состояние
        TEST_POST.setStatus(actualStatus);
    }

    @Test
    void findByUser() {
        when(postRepository.findAll()).thenReturn(List.of(TEST_POST, SECOND_TEST_POST));
        assertEquals(List.of(TEST_POST), postService.findByUser(TEST_ID));
    }
}