package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.repository.CategoryRepository;
import com.springboot.bozon.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author mialyshev
 */

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.getOne(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public boolean save(Category category) {
        Optional<Category> categoryFromDb = categoryRepository.findByName(category.getName());

        if (categoryFromDb.isPresent()) {
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
