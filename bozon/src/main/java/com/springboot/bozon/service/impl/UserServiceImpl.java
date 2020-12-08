package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Comment;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.RoleRepository;
import com.springboot.bozon.repository.UserRepository;
import com.springboot.bozon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author mialyshev
 */

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public boolean save(User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER"));
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPresentByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isPresentByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Override
    @Transactional
    public void updateUserInfo(User user, Long userId) {
        User userFromDB = findById(userId);
        // FIXME: а если null? мб на Optional переписать?
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPhoneNumber(user.getPhoneNumber());
        if (user.getRole() != null) {
            userFromDB.setRole(user.getRole());
        }
    }

    @Override
    public int getAVGRating(Long id) {
        User userFromDB = userRepository.getOne(id);
        // FIXME: зачем копия?
        List<Comment> comments = List.copyOf(userFromDB.getRating());
        return  (int) comments
                .stream()
                .mapToInt(Comment::getRating)
                .average()
                .orElse(0);
    }

}
