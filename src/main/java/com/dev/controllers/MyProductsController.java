package com.dev.controllers;

import com.dev.models.MyOfferModel;
import com.dev.models.ProductModel;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class MyProductsController {
    @Autowired
    private Persist persist;

    @RequestMapping(value = "get-my-products", method = RequestMethod.GET)
    public List<ProductModel> getMyProducts(String token) {
        return persist.getMyProductsFromTable(token);
    }
}
