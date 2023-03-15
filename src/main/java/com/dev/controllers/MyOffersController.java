package com.dev.controllers;

import com.dev.models.MyOfferModel;
import com.dev.models.MyProductsModel;
import com.dev.objects.User;
import com.dev.responses.BasicResponse;
import com.dev.responses.MyOffersResponse;
import com.dev.responses.MyProductsResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dev.utils.Errors.ERROR_ADMIN_NOT_RELEVANT;
import static com.dev.utils.Errors.ERROR_NO_SUCH_TOKEN;

@RestController
public class MyOffersController {
    @Autowired
    private Persist persist;
    @RequestMapping(value = "/get-my-offers", method = {RequestMethod.GET})
    public BasicResponse getMyOffers(String token) {
        BasicResponse response;
        User user = persist.getUserByToken(token);
        if (user != null){
            if (!user.isAdmin()) {
                List<MyOfferModel> myOffers = persist.getMyOffers(token);
                response = new MyOffersResponse(true, null, myOffers);
            } else {
                response = new BasicResponse(false, ERROR_ADMIN_NOT_RELEVANT);
            }
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }
}
