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
    @Column
    private boolean isAdmin;

    public User(String username, String token, boolean isAdmin) {
        this.username = username;
        this.token = token;
        this.credit = isAdmin ? 0d : 1000d;
        this.isAdmin = isAdmin;
    }
    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10.0, places);
        return (double)Math.round(value * scale) / scale;
    }
    public User() {

    }
}
