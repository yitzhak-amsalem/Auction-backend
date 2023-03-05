package com.dev.objects;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table (name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String imageLink;
    @Column
    private Integer price;
    @ManyToOne
    @JoinColumn
    private User owner;

    public Product(String name, String description, String imageLink, Integer price, User owner) {
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.price = price;
        this.owner = owner;
    }

    public Product() {

    }
}
