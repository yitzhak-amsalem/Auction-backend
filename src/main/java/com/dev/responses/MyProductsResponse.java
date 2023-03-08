package com.dev.responses;

import com.dev.models.MyProductsModel;
import lombok.Data;

import java.util.List;

@Data
public class MyProductsResponse extends BasicResponse {
    private List<MyProductsModel> myProducts;

    public MyProductsResponse(boolean success, Integer errorCode, List<MyProductsModel> myProducts) {
        super(success, errorCode);
        this.myProducts = myProducts;
    }

}
