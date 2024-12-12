package com.example.Supermarket.controller;



// import com.example.Supermarket.entity.Order;
import com.example.Supermarket.entity.User;
import com.example.Supermarket.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        User savedUser = userService.addNewUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<User> getById(@PathVariable(name = "id") Integer userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // @GetMapping("/getOrdersByUserId/{id}")
    // public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable(name = "id") Integer userId) {
    //     List<Order> orders = userService.getOrdersByUserId(userId);
    //     return ResponseEntity.ok(orders);
    // }
}

