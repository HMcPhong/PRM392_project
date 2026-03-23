package com.PRM392.prm392.repository;

import com.PRM392.prm392.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductID(Integer productID);
    List<Product> findAllByProductName(String productName);
    List<Product> findAllByCategoryID(int categoryID);
}
