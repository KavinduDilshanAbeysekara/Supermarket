package com.example.Supermarket.service;



import com.example.Supermarket.dto.ProductDTO;
import com.example.Supermarket.entity.Order;
import com.example.Supermarket.entity.Product;
import com.example.Supermarket.entity.User;
import com.example.Supermarket.repository.OrderRepository;
import com.example.Supermarket.repository.ProductRepository;
import com.example.Supermarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new IllegalArgumentException("No such product found with ID: " + productId);
        }
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        Product existingProduct = productRepository.findById(updatedProduct.getId()).orElse(null);
        if (existingProduct != null) {
            return productRepository.save(updatedProduct);
        } else {
            throw new IllegalArgumentException("No such product found with ID: " + updatedProduct.getId());
        }
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> 
            new IllegalArgumentException("No product found with ID: " + productId)
        );
    }

    @Override
    public Order purchaseProducts(Integer userId, List<ProductDTO> productsList) {
        User user = userRepository.findById(userId).orElseThrow(() -> 
            new IllegalArgumentException("No user found with ID: " + userId)
        );

        Order order = new Order();
        order.setTotalPrice(0.0);
        order.setProducts(new ArrayList<>());

        for (ProductDTO productDTO : productsList) {
            Product product = productRepository.findById(productDTO.getProductId()).orElseThrow(() -> 
                new IllegalArgumentException("No product found with ID: " + productDTO.getProductId())
            );

            order.setTotalPrice(order.getTotalPrice() + product.getPrice() * productDTO.getQuantityToPurchase());
            order.getProducts().add(product);
        }

        order.setDeliveryStatus("Pending");
        order.setOrderTime(LocalDateTime.now());
        orderRepository.save(order);

        user.getOrders().add(order);
        userRepository.save(user);

        return order;
    }
}

