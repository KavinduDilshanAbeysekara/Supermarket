package com.example.Supermarket.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.Supermarket.entity.Order;
@Service
public interface OrderService {
    List<Order> getAllOrders();
    Order createOrder(Order order);
}
