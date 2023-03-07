package com.dev.responses;

import com.dev.objects.User;
import lombok.Data;

@Data
public class UserDetailsResponse extends BasicResponse{
    Double credit;
    String username;
    public UserDetailsResponse(boolean success, Integer errorCode, User user) {
        super(success, errorCode);
        this.credit = user.getCredit();
        this.username = user.getUsername();
    }
}
