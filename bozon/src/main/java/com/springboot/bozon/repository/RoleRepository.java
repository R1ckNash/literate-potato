package com.springboot.bozon.repository;

import com.springboot.bozon.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author mialyshev
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
