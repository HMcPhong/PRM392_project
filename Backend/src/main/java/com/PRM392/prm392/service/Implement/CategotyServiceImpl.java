package com.PRM392.prm392.service.Implement;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.repository.CategoriesRepository;
import com.PRM392.prm392.request.create.Category.CategoryCreateRequest;
import com.PRM392.prm392.service.Interface.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategotyServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;

    public CategotyServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    public Categories addCategory(CategoryCreateRequest request) {
        Categories categories = new Categories();
        categories.setCategory_name(request.getCategoryName());

        categoriesRepository.save(categories);

        return categories;
    }

    @Override
    public Categories getAllCategories() {
        return null;
    }
}
