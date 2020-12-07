package com.springboot.bozon.service.impl;

import com.springboot.bozon.model.Post;
import com.springboot.bozon.model.Sale;
import com.springboot.bozon.model.Status;
import com.springboot.bozon.model.User;
import com.springboot.bozon.repository.SaleRepository;
import com.springboot.bozon.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mialyshev
 */
@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale findById(Long id) {
        return saleRepository.getOne(id);
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public boolean save(Post post, User user) {
        Sale sale = new Sale();
        sale.setStatus(Status.ACTIVE);
        sale.setBuyer(user);
        sale.setPost(post);
        saleRepository.save(sale);
        return true;
    }

    public boolean deleteById(Long id) {
        if (saleRepository.findById(id).isPresent()) {
            saleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
