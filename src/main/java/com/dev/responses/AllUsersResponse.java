package com.dev.responses;

import com.dev.models.UserForAdminModel;
import com.dev.objects.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AllUsersResponse extends BasicResponse {
    private List<UserForAdminModel> users;

    public AllUsersResponse(boolean success, Integer errorCode, List<UserForAdminModel> users) {
        super(success, errorCode);
        this.users = users;
    }
}
