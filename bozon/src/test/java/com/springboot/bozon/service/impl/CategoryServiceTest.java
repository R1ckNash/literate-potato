package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Category;
import com.springboot.bozon.repository.CategoryRepository;
import com.springboot.bozon.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(value = CategoryServiceImpl.class)
class CategoryServiceTest {
    private static final Category TEST_CATEGORY;
    private static final long TEST_ID = 1L;
    private static final String TEST_NAME = "TEST_NAME";

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    static {
        TEST_CATEGORY = new Category();
        TEST_CATEGORY.setId(TEST_ID);
        TEST_CATEGORY.setName(TEST_NAME);
    }

    @Test
    void findById() {
        when(categoryRepository.getOne(TEST_ID)).thenReturn(TEST_CATEGORY);
        assertEquals(TEST_CATEGORY, categoryService.findById(TEST_ID));
        assertNull(categoryService.findById(2L));
    }

    @Test
    void findAll() {
        when(categoryRepository.findAll()).thenReturn(List.of(TEST_CATEGORY));
        assertEquals(List.of(TEST_CATEGORY), categoryService.findAll());
    }

    @Test
    void save() {
        when(categoryRepository.findByName(TEST_NAME)).thenReturn(Optional.of(TEST_CATEGORY));
        assertFalse(categoryService.save(TEST_CATEGORY));
        Category category = new Category();
        category.setName("never existed");
        assertTrue(categoryService.save(category));
    }

    @Test
    void deleteById() {
        when(categoryRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_CATEGORY));
        assertTrue(categoryService.deleteById(TEST_ID));
        assertFalse(categoryService.deleteById(2L));
    }
}