package com.dev.controllers;


import com.dev.objects.User;
import com.dev.responses.AllUsersResponse;
import com.dev.responses.BasicResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ManageController {

    @Autowired
    private Persist persist;

    @RequestMapping (value = "get-all-users", method = RequestMethod.GET)
    public BasicResponse getAllUsers () {
        List<User> users = persist.getAllUsers();
        AllUsersResponse allUsersResponse = new AllUsersResponse(true, null, users);
        return allUsersResponse;
    }
}
