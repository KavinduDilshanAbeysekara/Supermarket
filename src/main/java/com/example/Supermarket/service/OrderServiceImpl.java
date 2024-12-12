package com.example.Supermarket.service;



import com.example.Supermarket.entity.Order;
import com.example.Supermarket.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new IllegalStateException("No orders found within the database");
        }
        return orders;
    }

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            orderRepository.delete(optionalOrder.get());
        } else {
            throw new IllegalArgumentException("No such order found against this ID");
        }
    }

    @Override
    public Order updateOrder(Order order) {
        Optional<Order> existingOrder = orderRepository.findById(order.getOrderNumber());
        if (existingOrder.isPresent()) {
            return orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("No such order found against this ID");
        }
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findById(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("No such order found within the database"));
    }
}

