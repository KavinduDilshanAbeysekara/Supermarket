package com.example.Supermarket.service;

import java.util.List;

import com.example.Supermarket.entity.Category;
import com.example.Supermarket.entity.Product;

public interface CategoryService {

 List<Category> findAllCategories();
 Category addCategory(Category category);
 String deleteCategory(String CategoryId);
 Category updateCategory(Category category);
 Category getCategoryById(String categoryId);
String addProductsInCategory(String categoryId, List<Product> productList);   
    
} 
