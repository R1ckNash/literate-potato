package com.springboot.bozon.service;

import com.springboot.bozon.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mialyshev
 */

@Service
public interface UserService{
    boolean save(User user);

    User findByUsername(String username);

    List<User> getAll();

    User findById(Long id);

    boolean delete(Long id);

    boolean findByEmail(String email);

    boolean findByPhoneNumber(String phoneNumber);

    boolean updateUserInfo(User user, Long userId);

}
