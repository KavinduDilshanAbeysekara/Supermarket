package com.example.Supermarket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Supermarket.entity.Product;

@Service
public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    Product getProductById(Long id);
    void deleteProduct(Long id);
}
