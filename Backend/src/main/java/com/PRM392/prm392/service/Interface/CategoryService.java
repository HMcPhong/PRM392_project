package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.request.create.Category.CategoryCreateRequest;

import java.util.List;

public interface CategoryService {
    Categories addCategory(CategoryCreateRequest category);

    Categories getCategoryByCategory_id(int id);

    List<Categories> getAllCategories();
}
