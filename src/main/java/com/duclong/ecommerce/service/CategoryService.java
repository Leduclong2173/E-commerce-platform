package com.duclong.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Category;
import com.duclong.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory(){
        return this.categoryRepository.findAll();
    }

    public boolean checkNameExist(String name){
        return this.categoryRepository.existsByName(name);
    }

    public Category handleSaveCategory(Category category){
        return this.categoryRepository.save(category);
    }

    public List<Category> searchByNameOrId(String keyword) {
        if (keyword == null){
            return getAllCategory();
        }
        Long id = null;
        try {
            id = Long.parseLong(keyword);
        } catch (NumberFormatException e) {
        }
        return this.categoryRepository.searchByNameOrId(keyword, id);
    }

    public void deleteCategoryById(long id){
        this.categoryRepository.deleteById(id);
    }

    public Category getCategoryById(long id){
        return this.categoryRepository.findById(id);
    }

    public Category getCategoryByName(String name){
        return this.categoryRepository.findByName(name);
    }

    public long countCategories(){
        return this.categoryRepository.count();
    }
}
