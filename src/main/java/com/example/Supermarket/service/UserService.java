package com.example.Supermarket.service;



import org.springframework.stereotype.Service;

import com.example.Supermarket.entity.User;

@Service
public interface UserService {
    User createUser(User user);
}
