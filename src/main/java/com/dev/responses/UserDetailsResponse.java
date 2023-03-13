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
        this.credit = user.roundAvoid(user.getCredit(), 2);
        this.username = user.getUsername();
        this.isAdmin = user.isAdmin();
    }
    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10.0, (double)places);
        return (double)Math.round(value * scale) / scale;
    }
}
