package com.example.Supermarket.controller;



import com.example.Supermarket.dto.ProductDTO;
import com.example.Supermarket.entity.Product;
import com.example.Supermarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/update")
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product successfully deleted.");
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<Product> getById(@PathVariable(name = "id") String productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(
            @RequestParam(name = "userId") Integer userId,
            @RequestBody List<ProductDTO> productList) {
        productService.purchaseProducts(userId, productList);
        return ResponseEntity.ok("Products successfully purchased.");
    }
}

