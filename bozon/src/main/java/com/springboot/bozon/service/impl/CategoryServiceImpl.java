package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.repository.CategoryRepository;
import com.springboot.bozon.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mialyshev
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long id) {
        return categoryRepository.getOne(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public boolean save(Category category) {
        Category categoryFromDB = categoryRepository.findByName(category.getName());

        if (categoryFromDB != null) {
            return false;
        }
        category.setStatus(Status.ACTIVE);
        categoryRepository.save(category);
        return true;
    }

    public boolean deleteById(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
