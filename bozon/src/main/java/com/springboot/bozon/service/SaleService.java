package com.springboot.bozon.service;

import com.springboot.bozon.model.Sale;
import com.springboot.bozon.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ricknash
 */
@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale findById(Long id) {
        return saleRepository.getOne(id);
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }
}
