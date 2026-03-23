package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.request.create.Category.CategoryCreateRequest;

public interface CategoryService {
    Categories addCategory(CategoryCreateRequest category);
    Categories getAllCategories();
}
