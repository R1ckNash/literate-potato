package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Comment;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.RoleRepository;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(value = UserServiceImpl.class)
@MockBeans(value = {@MockBean(RoleRepository.class), @MockBean(BCryptPasswordEncoder.class)})
class UserServiceTest {

    private static final String TEST_USERNAME = "test";
    private static final User TEST_USER;
    private static final long TEST_USER_ID = 1L;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    static {
        TEST_USER = new User();
        TEST_USER.setEmail("test@test.com");
        TEST_USER.setFirstName("Abraham");
        TEST_USER.setLastName("Lincoln");
        TEST_USER.setPhoneNumber("88005553535");
        TEST_USER.setUsername(TEST_USERNAME);
        Set<Comment> comments = new HashSet<>();
        for (int i=1; i < 6; i++) {
            Comment comment = new Comment();
            comment.setRated_user(TEST_USER);
            comment.setRating(i);
            comment.setId((long) i);
            comments.add(comment);
        }
        TEST_USER.setRating(comments);
    }

    @Test
    void saveAbsentUser() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertTrue(userService.save(TEST_USER));
    }

    @Test
    void saveExistingUser() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(TEST_USER));
        assertFalse(userService.save(TEST_USER));
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(TEST_USER));
        assertEquals(TEST_USER, userService.findByUsername(TEST_USERNAME));
        assertNull(userService.findByUsername("NOT_TEST_USER"));
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(List.of(TEST_USER));
        assertEquals(List.of(TEST_USER), userService.getAll());
    }

    @Test
    void findById() {
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(TEST_USER));
        assertEquals(TEST_USER, userService.findById(TEST_USER_ID));
        assertNull(userService.findById(2L));
    }

    @Test
    void delete() {
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(TEST_USER));
        assertTrue(userService.delete(TEST_USER_ID));
        assertFalse(userService.delete(2L));
    }

    @Test
    void isPresentByEmail() {
        when(userRepository.findByEmail(TEST_USER.getEmail())).thenReturn(Optional.of(TEST_USER));
        assertTrue(userService.isPresentByEmail(TEST_USER.getEmail()));
        assertFalse(userService.isPresentByEmail("NOT_TEST_EMAIL"));
    }

    @Test
    void isPresentByPhoneNumber() {
        when(userRepository.findByEmail(TEST_USER.getPhoneNumber())).thenReturn(Optional.of(TEST_USER));
        assertTrue(userService.isPresentByEmail(TEST_USER.getPhoneNumber()));
        assertFalse(userService.isPresentByEmail("NOT_TEST_PHONE"));
    }

    @Test
    void updateUserInfo() {
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(TEST_USER));
        userService.updateUserInfo(TEST_USER, 1L);
    }

    @Test
    void getAVGRating() {
        when(userRepository.getOne(TEST_USER_ID)).thenReturn(TEST_USER);
        assertEquals(3, userService.getAVGRating(TEST_USER_ID));
    }
}