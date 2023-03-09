package com.dev.responses;

import com.dev.objects.User;
import lombok.Data;

@Data
public class UserDetailsResponse extends BasicResponse {
    private Double credit;
    private String username;
    private boolean isAdmin;
    public UserDetailsResponse(boolean success, Integer errorCode, User user) {
        super(success, errorCode);
        this.credit = user.getCredit();
        this.username = user.getUsername();
        this.isAdmin = user.isAdmin();
    }
}
