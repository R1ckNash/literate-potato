package com.springboot.bozon.service;

import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ricknash
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findByLastName(String lastName) {
        List<User> all = userRepository.findAll();
        List<User> result = new ArrayList<>();
        for (User user : all) {
            if (lastName.equals(user.getLastName())){
                result.add(user);
            }
        }
        return result;
    }

}
