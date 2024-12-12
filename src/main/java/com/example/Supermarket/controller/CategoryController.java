package com.example.Supermarket.controller;

import com.example.Supermarket.entity.Category;
// import com.example.Supermarket.entity.Product;
import com.example.Supermarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Category>> list() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/save")
    public ResponseEntity<Category> save(@RequestBody Category category) {
        Category savedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String categoryId) {
        String message = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<Category> getById(@PathVariable(name = "id") String categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    // @PostMapping("/addProducts")
    // public ResponseEntity<String> addProducts(@RequestParam(name = "categoryId") String categoryId, @RequestBody List<Product> productList) {
    //     String message = categoryService.addProductsInCategory(categoryId, productList);
    //     return ResponseEntity.ok(message);
    // }
}
