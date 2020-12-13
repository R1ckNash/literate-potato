package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.details.MyUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(value = UserDetailsServiceImpl.class)
class UserDetailsServiceTest {

    private static final String TEST_USERNAME = "test";

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        User user = new User();
        MyUserDetails expectedDetails = new MyUserDetails(user);
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        assertEquals(expectedDetails, userDetailsService.loadUserByUsername(TEST_USERNAME));
        Throwable throwable = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("not test"));
        assertEquals("Could not find user", throwable.getMessage());
    }
}