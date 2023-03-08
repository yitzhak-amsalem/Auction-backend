package com.dev.models;

import com.dev.objects.User;
import lombok.Data;

@Data
public class UserModel {
    private int id;
    private String username;

    public UserModel(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

}
