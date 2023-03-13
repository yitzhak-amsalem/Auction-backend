package com.dev.controllers;


import com.dev.models.UserForAdminModel;
import com.dev.objects.User;
import com.dev.responses.*;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dev.utils.Errors.*;

@RestController
public class ManageController {

    @Autowired
    private Persist persist;

    @RequestMapping (value = "get-all-users", method = RequestMethod.GET)
    public BasicResponse getAllUsers (String token) {
        BasicResponse response;
        User admin = persist.getUserByToken(token);
        if (admin != null){
            if (admin.isAdmin()){
                List<UserForAdminModel> users = persist.getAllUsers();
                response = new AllUsersResponse(true, null, users);
            } else {
                response = new BasicResponse(false, ERROR_NO_ADMIN);
            }
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }
    @RequestMapping (value = "update-user-credit", method = RequestMethod.POST)
    public BasicResponse updateUserCredit (String adminToken, String userToken, Double amount) {
        BasicResponse response;
        User admin = persist.getUserByToken(adminToken);
        if (admin != null){
            if (admin.isAdmin()){
                User user = persist.getUserByToken(userToken);
                if (user != null){
                    persist.updateUserCredit(user, amount);
                    response = new UpdateCreditResponse(true, null, amount);
                } else {
                    response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
                }
            } else {
                response = new BasicResponse(false, ERROR_NO_ADMIN);
            }
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN_FOR_ADMIN);
        }
        return response;
    }
}
