package com.springboot.bozon.service;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Sale;
import com.springboot.bozon.model.User;

import java.util.List;

/**
 * @author mialyshev
 */
public interface SaleService {
    Sale findById(Long id);

    List<Sale> findAll();

    void save(Post post, User user);

    boolean deleteById(Long id);

}
