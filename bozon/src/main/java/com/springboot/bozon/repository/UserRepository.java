package com.springboot.bozon.repository;

import com.springboot.bozon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author ricknash
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
