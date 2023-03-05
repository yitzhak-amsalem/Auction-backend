package com.dev.controllers;

import com.dev.objects.Product;
import com.dev.responses.AllUsersResponse;
import com.dev.responses.BasicResponse;
import com.dev.responses.MyProductsResponse;
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
    public BasicResponse getAllUsers (String token) {
        List<Product> myProducts = persist.getMyProductsFromTable(token);
        MyProductsResponse myProductsResponse = new MyProductsResponse(true, null, myProducts);
        return myProductsResponse;
    }
}
