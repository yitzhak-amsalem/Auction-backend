package com.dev.models;

import com.dev.objects.User;
import lombok.*;

@Data
@Builder
public class UserForAdminModel {
    private Double credit;
    private String username;
    private String token;
    private Integer sumOffers;

}
