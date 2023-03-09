package com.dev.models;

import lombok.*;

@Data
@Builder
public class UserForAdminModel {
    private Double credit;
    private String username;
    private String token;
    private Integer sumAuctions;

}
