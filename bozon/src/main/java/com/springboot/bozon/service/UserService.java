package com.springboot.bozon.service;

import com.springboot.bozon.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mialyshev
 */

@Service
public interface UserService {
    boolean save(User user);

    User findByUsername(String username);

    List<User> getAll();

    User findById(Long id);

    boolean delete(Long id);

    boolean isPresentByEmail(String email);

    boolean isPresentByPhoneNumber(String phoneNumber);

    void updateUserInfo(User user, Long userId);

    int getAVGRating(Long id);
}
