package com.PRM392.prm392.service.Implement;

import com.PRM392.prm392.entity.Categories;
import com.PRM392.prm392.repository.CategoriesRepository;
import com.PRM392.prm392.request.create.Category.CategoryCreateRequest;
import com.PRM392.prm392.service.Interface.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    public Categories addCategory(CategoryCreateRequest request) {
        Categories categories = new Categories();
        categories.setCategoryName(request.getCategoryName());

        categoriesRepository.save(categories);

        return categories;
    }

    @Override
    public Categories getCategoryByCategoryId(int id) {
        return categoriesRepository.findByCategoryId(id);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }
}
