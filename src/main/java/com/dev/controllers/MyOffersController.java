package com.dev.controllers;

import com.dev.models.MyOfferModel;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyOffersController {
    @Autowired
    private Persist persist;
    @RequestMapping(value = "/get-my-offers", method = {RequestMethod.GET})
    public List<MyOfferModel> getMyOffers(String token) {
        return persist.getMyOffers(token);
    }
}
