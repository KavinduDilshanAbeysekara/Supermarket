package com.example.Supermarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Supermarket.entity.Order;
import com.example.Supermarket.entity.User;
import com.example.Supermarket.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user found against this ID"));

        user.setOrders(null);
        userRepository.delete(user);
    }

    @Override
    public void deleteUser(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("No such user found in the database");
        }
    }

    @Override
    public User updateUser(User updatedUser) {

        userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("No such user found against this ID"));

        return userRepository.save(updatedUser);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user found within the database"));
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user found within the database"));
        List<Order> orders = user.getOrders();
        if (orders.isEmpty()) {
            throw new IllegalStateException("No orders made yet by this user");
        }
        return orders;
    }
}
