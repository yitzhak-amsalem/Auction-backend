package com.dev.controllers;

import com.dev.models.StatisticsModel;
import com.dev.objects.User;
import com.dev.responses.BasicResponse;
import com.dev.responses.LoginResponse;
import com.dev.responses.StatisticsResponse;
import com.dev.responses.UserDetailsResponse;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.dev.utils.Errors.*;

@RestController
public class LoginController {
    @Autowired
    private Utils utils;
    @Autowired
    private Persist persist;

    @RequestMapping(value = "sign-up", method = RequestMethod.POST)
    public BasicResponse signUp (String username, String password) {
        BasicResponse response;
        if (username != null) {
            if (password != null) {
                if (utils.isStrongPassword(password)) {
                    User fromDb = persist.getUserByUsername(username);
                    if (fromDb == null) {
                        persist.addUser(username, password, false);
                        response = new BasicResponse(true, null);
                    } else {
                        response = new BasicResponse(false, ERROR_USERNAME_ALREADY_EXISTS);
                    }
                } else {
                    response = new BasicResponse(false, ERROR_WEAK_PASSWORD);
                }
            } else {
                response = new BasicResponse(false, ERROR_MISSING_PASSWORD);
            }
        } else {
            response = new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
        return response;
    }

    @RequestMapping (value = "login", method = RequestMethod.POST)
    public BasicResponse login (String username, String password) {
        BasicResponse response;
        if (username != null) {
            if (password != null) {
                String token = utils.createHash(username, password);
                User user = persist.getUserByToken(token);
                if (user != null && !user.isAdmin()) {
                    response = new LoginResponse(true, null, token);
                } else {
                    response = new BasicResponse(false, ERROR_WRONG_LOGIN_CREDS);
                }
            } else {
                response = new BasicResponse(false, ERROR_MISSING_PASSWORD);
            }
        } else {
            response = new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
        return response;
    }
    @RequestMapping (value = "login-as-admin")
    public BasicResponse loginAsAdmin (String username, String password) {
        BasicResponse response;
        if (username != null) {
            if (password != null) {
                String token = utils.createHash(username, password);
                User admin = persist.getUserByToken(token);
                if (admin != null && admin.isAdmin()) {
                    response = new LoginResponse(true, null, token);
                } else {
                    response = new BasicResponse(false, ERROR_WRONG_LOGIN_CREDS);
                }
            } else {
                response = new BasicResponse(false, ERROR_MISSING_PASSWORD);
            }
        } else {
            response = new BasicResponse(false, ERROR_MISSING_USERNAME);
        }
        return response;
    }
    @RequestMapping (value = "get-statistics", method = RequestMethod.GET)
    public BasicResponse getStatistics () {
        StatisticsModel statisticsModel = StatisticsModel.builder()
                .numOfAuction(persist.getNumberOfAuctions())
                .numOfOffers(persist.getNumberOfOffers())
                .numOfUsers(persist.getNumberOfUsers())
                .build();
        return new StatisticsResponse(true, null, statisticsModel);
    }

    @RequestMapping(value = "/get-user-details", method = {RequestMethod.GET})
    public BasicResponse getUserDetails(String token) {
        BasicResponse response;
        User user = persist.getUserByToken(token);
        if (user != null){
            response = new UserDetailsResponse(true, null, user);
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }

}
