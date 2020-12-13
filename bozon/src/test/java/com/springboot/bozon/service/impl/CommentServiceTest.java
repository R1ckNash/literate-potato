package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Comment;
import com.springboot.bozon.model.Sale;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.CommentRepository;
import com.springboot.bozon.repository.SaleRepository;
import com.springboot.bozon.service.CommentService;
import com.springboot.bozon.service.SaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(value = CommentServiceImpl.class)
class CommentServiceTest {

    private static final Comment TEST_COMMENT;
    private static final long TEST_ID = 1L;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    static {
        TEST_COMMENT = new Comment();
        TEST_COMMENT.setId(TEST_ID);
    }


    @Test
    void findById() {
        when(commentRepository.getOne(TEST_ID)).thenReturn(TEST_COMMENT);
        assertEquals(TEST_COMMENT, commentService.findById(TEST_ID));
        assertNull(commentService.findById(2L));
    }

    @Test
    void findAll() {
        when(commentRepository.findAll()).thenReturn(List.of(TEST_COMMENT));
        assertEquals(List.of(TEST_COMMENT), commentService.findAll());
    }

    @Test
    void save() {
        commentService.save(new User(), new User(), TEST_COMMENT);
    }

    @Test
    void deleteById() {
        when(commentRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_COMMENT));
        assertTrue(commentService.deleteById(TEST_ID));
        assertFalse(commentService.deleteById(2L));
    }
}