package com.dev.objects;

import lombok.*;

import javax.persistence.*;

@Data
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
}
