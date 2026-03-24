package com.PRM392.prm392.service.Implement;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.entity.Product;
import com.PRM392.prm392.repository.ProductRepository;
import com.PRM392.prm392.request.create.Product.ProductCreateRequest;
import com.PRM392.prm392.service.Interface.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product addProduct(ProductCreateRequest request) {

        Product product = new Product();
        product.setProductName(request.getProductName());
        if(request.getBriefDescription() != null) product.setBriefDescription(request.getBriefDescription());
        if(request.getFullDescription() != null) product.setFullDescription(request.getFullDescription());
        if(request.getTechnicalSpecification() != null) product.setTechnicalSpecifications(request.getTechnicalSpecification());
        product.setPrice(request.getPrice());
        if (request.getImageURL() != null) product.setImageUrl(request.getImageURL());
        product.setCategoryID(request.getCategoryID());

        productRepository.save(product);

        return product;

    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findByProductID(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Categories category) {
        return productRepository.findAllByCategoryID(category.getCategoryId());
    }

    @Override
    public List<Product> getProductsByProductName(String productName) {
        return productRepository.findAllByProductNameContainingIgnoreCase(productName);
    }
}
