package com.PRM392.prm392.controller;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.request.create.Category.CategoryCreateRequest;
import com.PRM392.prm392.response.ResponseData;
import com.PRM392.prm392.service.Interface.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ResponseData<?>> createCategory(@RequestBody CategoryCreateRequest request) {
        try {
            Categories categories = categoryService.addCategory(request);
            return ResponseEntity.ok(new ResponseData<>("Category created successfully", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseData<?>> getAllCategories() {
        try {
            List<Categories> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ResponseData<>("Category List", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    @GetMapping("/search/{categoryID}")
    public ResponseEntity<ResponseData<?>> getCategoryById(@PathVariable("categoryID") int categoryID) {
        try {
            Categories categories = categoryService.getCategoryByCategoryId(categoryID);
            return ResponseEntity.ok(new ResponseData<>("Category found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

}
