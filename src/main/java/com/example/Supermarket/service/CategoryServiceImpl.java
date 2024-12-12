package com.example.Supermarket.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Supermarket.entity.Category;
import com.example.Supermarket.entity.Product;
import com.example.Supermarket.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new RuntimeException("No categories found within the database.");

        }
        return categories;
    }

    @Override
    public Category addCategory(Category category){
        try{
            return categoryRepository.save(category);
        }catch (Exception e){
            throw new RuntimeException("Failed to add category :" + e.getMessage());
        }
    }

    @Override
    public String deleteCategory(String categoryId){
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            try{
                categoryRepository.delete(categoryOptional.get());
                return "Successfully delted category from the database.";
            }catch (Exception e){
                throw new RuntimeException("Failed to delete category:" + e.getMessage());
            }
        }else{
            throw new RuntimeException("No such category found against this ID.");
        }
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getId());
        if (existingCategory.isPresent()) {
            try {
                return categoryRepository.save(category);
            } catch (Exception e) {
                throw new RuntimeException("Failed to update category: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("No such category found against this ID.");
        }
    }

    @Override
    public Category getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("No such category found within the database."));
    }

    @Override
    public String addProductsInCategory(String categoryId, List<Product> productList) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            if (productList.isEmpty()) {
                throw new RuntimeException("The product list is empty.");
            }
            try {
                category.getProducts().addAll(productList);
                categoryRepository.save(category);
                return "Successfully added products within the category.";
            } catch (Exception e) {
                throw new RuntimeException("Failed to add products: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("No such category found within the database.");
        }
    }
}
