package com.springboot.bozon.repository;

import com.springboot.bozon.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mialyshev
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
