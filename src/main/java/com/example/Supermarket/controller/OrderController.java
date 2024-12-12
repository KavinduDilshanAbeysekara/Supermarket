package com.example.Supermarket.controller;


import com.example.Supermarket.entity.Order;
import com.example.Supermarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> list() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/save")
    public ResponseEntity<Order> save(@RequestBody Order order) {
        Order savedOrder = orderService.addOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @PutMapping("/update")
    public ResponseEntity<Order> update(@RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order successfully deleted.");
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<Order> getById(@PathVariable(name = "id") String orderId) {
        Order order = orderService.getOrderByOrderNumber(orderId);
        return ResponseEntity.ok(order);
    }
}

