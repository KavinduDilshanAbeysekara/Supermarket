package com.example.Supermarket.service;



import com.example.Supermarket.dto.ProductDTO;
import com.example.Supermarket.entity.Order;
import com.example.Supermarket.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product addProduct(Product product);

    void deleteProduct(String productId);

    Product updateProduct(Product updatedProduct);

    Product getProductById(Long productId);

    Order purchaseProducts(Integer userId, List<ProductDTO> productsList);
}

