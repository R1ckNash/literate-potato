package com.springboot.bozon.repository;

import com.springboot.bozon.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ricknash
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
