package com.dev.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String username;
    @Column
    private String token;
    @Column
    private Double credit;

    public User() {
        this.credit = 1000d;
    }

    public User(String username, String token) {
        this.username = username;
        this.token = token;
        this.credit = 1000d;
    }
}
