package com.springboot.bozon.repository;

import com.springboot.bozon.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mialyshev
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
