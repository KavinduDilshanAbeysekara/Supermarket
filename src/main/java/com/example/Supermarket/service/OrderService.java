package com.example.Supermarket.service;

import java.util.List;

import com.example.Supermarket.entity.Order;

public interface OrderService {
    List<Order> findAllOrders();

    Order addOrder(Order order);

    void deleteOrder(String orderId);

    Order updateOrder(Order order);

    Order getOrderByOrderNumber(String orderNumber);

}
