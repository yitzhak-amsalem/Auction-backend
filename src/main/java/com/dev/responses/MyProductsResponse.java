package com.dev.responses;

import com.dev.models.Product;

import java.util.List;


    public class MyProductsResponse extends BasicResponse {
        private List<Product> myProducts;

        public MyProductsResponse(boolean success, Integer errorCode, List<Product> myProducts) {
            super(success, errorCode);
            this.myProducts = myProducts;
        }

    }
