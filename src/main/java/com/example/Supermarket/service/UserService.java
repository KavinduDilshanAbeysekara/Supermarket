package com.example.Supermarket.service;

import java.util.List;

import com.example.Supermarket.entity.Order;
import com.example.Supermarket.entity.User;

public interface UserService {
    List<User> findAllUsers();
    User addNewUser(User user);
    void deleteUserById(Integer userId);
    void deleteUser(User user);
    User updateUser(User updatedUser);
    User getUserById(Integer userId);
    List<Order> getOrdersByUserId(Integer userId);
}
