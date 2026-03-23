package com.PRM392.prm392.controller;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.entity.Product;
import com.PRM392.prm392.request.create.Product.ProductCreateRequest;
import com.PRM392.prm392.response.ResponseData;
import com.PRM392.prm392.service.Interface.CategoryService;
import com.PRM392.prm392.service.Interface.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ResponseData<?>> addProduct(@RequestBody ProductCreateRequest request) {
        try {
            Product product = productService.addProduct(request);
            return ResponseEntity.ok(new ResponseData<>("Product added", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{productID}")
    public ResponseEntity<ResponseData<?>> getProduct(@PathVariable int productID) {
        try {
            Product product = productService.getProduct(productID);
            return ResponseEntity.ok(new ResponseData<>("Product found", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseData<?>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ResponseData<>("Products found", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<ResponseData<?>> getProductByCategory(@PathVariable int category_id) {
        try {
            Categories categories = categoryService.getCategoryByCategory_id(category_id);
            List<Product> products = productService.getProductsByCategory(categories);
            return ResponseEntity.ok(new ResponseData<>("Products found", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData<?>> searchProduct(@RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByProductName(productName);
            return ResponseEntity.ok(new ResponseData<>("Products found", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

}
