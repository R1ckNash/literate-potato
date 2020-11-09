package com.springboot.bozon.service;

import com.springboot.bozon.model.Sale;

import java.util.List;

/**
 * @author mialyshev
 */
public interface SaleService {
    Sale findById(Long id);

    List<Sale> findAll();

    boolean save(Sale sale);

    boolean deleteById(Long id);

}
