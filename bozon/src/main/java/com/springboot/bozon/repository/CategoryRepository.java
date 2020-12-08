package com.springboot.bozon.repository;

import com.springboot.bozon.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author mialyshev
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
