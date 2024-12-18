package com.example.Supermarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToMany;

@Entity
@Getter
@Setter
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    private Double price;

    private String description;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "orderedProducts")
    private List<Order> orders;
}

