package com.example.Supermarket.service;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Supermarket.entity.Order;
import com.example.Supermarket.repository.OrderRepository;
@Service
public class OrderServiceImpl implements OrderService{
    
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}

