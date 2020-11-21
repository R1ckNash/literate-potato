package com.springboot.bozon.service;

import com.springboot.bozon.model.Category;

import java.util.List;

/**
 * @author mialyshev
 */
public interface CategoryService {
    Category findById(Long id);

    List<Category> findAll();

    boolean save(Category category);

    boolean deleteById(Long id);


}
