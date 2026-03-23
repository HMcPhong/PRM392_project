package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.entity.Product;
import com.PRM392.prm392.request.create.Product.ProductCreateRequest;

import java.util.List;

public interface ProductService {

    Product addProduct(ProductCreateRequest request);

    Product getProduct(int id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(Categories category);

    List<Product> getProductsByProductName(String productName);

}
