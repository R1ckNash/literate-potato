package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Sale;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.SaleRepository;
import com.springboot.bozon.service.SaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(value = SaleServiceImpl.class)
class SaleServiceTest {

    private static final Sale TEST_SALE;
    private static final long TEST_ID = 1L;

    @MockBean
    private SaleRepository saleRepository;

    @Autowired
    private SaleService saleService;

    static {
        TEST_SALE = new Sale();
        TEST_SALE.setId(TEST_ID);
    }

    @Test
    void findById() {
        when(saleRepository.getOne(TEST_ID)).thenReturn(TEST_SALE);
        assertEquals(TEST_SALE, saleService.findById(TEST_ID));
        assertNull(saleService.findById(2L));
    }

    @Test
    void findAll() {
        when(saleRepository.findAll()).thenReturn(List.of(TEST_SALE));
        assertEquals(List.of(TEST_SALE), saleService.findAll());
    }

    @Test
    void save() {
        saleService.save(new Post(), new User());
    }

    @Test
    void deleteById() {
        when(saleRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_SALE));
        assertTrue(saleService.deleteById(TEST_ID));
        assertFalse(saleService.deleteById(2L));
    }
}