package com.dev.objects;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table (name = "my_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String username;

    @Column
    private String token;

    public User() {
    }

    public User(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
