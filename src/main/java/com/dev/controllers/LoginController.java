package com.dev.controllers;

import com.dev.models.StatisticsModel;
import com.dev.objects.User;
import com.dev.responses.BasicResponse;
import com.dev.responses.LoginResponse;
import com.dev.responses.StatisticsResponse;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dev.utils.Errors.*;

@RestController
public class LoginController {
    @Autowired
    private Utils utils;
    @Autowired
    private Persist persist;

    @RequestMapping(value = "sign-up")
    public BasicResponse signUp (String username, String password) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        if (username != null) {
            if (password != null) {
                if (utils.isStrongPassword(password)) {
                    User fromDb = persist.getUserByUsername(username);
                    if (fromDb == null) {
                        persist.addUser(username, utils.createHash(username, password), false);
                        success = true;
                    } else {
                        errorCode = ERROR_USERNAME_ALREADY_EXISTS;
                    }
                } else {
                    errorCode = ERROR_WEAK_PASSWORD;
                }
            } else {
                errorCode = ERROR_MISSING_PASSWORD;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }

    @RequestMapping (value = "login")
    public BasicResponse login (String username, String password) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        if (username != null) {
            if (password != null) {
                String token = utils.createHash(username, password);
                User user = persist.getUserByUsernameAndToken(username, token);
                if (user != null && !user.isAdmin()) {
                    success = true;
                    basicResponse = new LoginResponse(token);
                } else {
                    errorCode = ERROR_WRONG_LOGIN_CREDS;
                }
            } else {
                errorCode = ERROR_MISSING_PASSWORD;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }
    @RequestMapping (value = "login-as-admin")
    public BasicResponse loginAsAdmin (String username, String password) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        if (username != null) {
            if (password != null) {
                String token = utils.createHash(username, password);
                User admin = persist.getUserByUsernameAndToken(username, token);
                if (admin != null && admin.isAdmin()) {
                    success = true;
                    basicResponse = new LoginResponse(token);
                } else {
                    errorCode = ERROR_WRONG_LOGIN_CREDS;
                }
            } else {
                errorCode = ERROR_MISSING_PASSWORD;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }
    @RequestMapping (value = "get-statistics")
    public BasicResponse getStatistics () { //todo errors?
        StatisticsModel statisticsModel = StatisticsModel.builder()
                .numOfAuction(persist.getNumberOfAuctions())
                .numOfOffers(persist.getNumberOfOffers())
                .numOfUsers(persist.getNumberOfUsers())
                .build();
        return new StatisticsResponse(true, null, statisticsModel);
    }

}
