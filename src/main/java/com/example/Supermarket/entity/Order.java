package com.example.Supermarket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Order {
    @Id
    private String orderNumber = UUID.randomUUID().toString();
    private Double totalPrice;
    private LocalDateTime orderTime;
    private String deliveryStatus;

    @ManyToMany(targetEntity = Product.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Product> products = new ArrayList<>();

    public Order(String orderNumber, Double totalPrice, LocalDateTime orderTime, String deliveryStatus) {
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.deliveryStatus = deliveryStatus;
    }

}
