package com.dev.responses;

import com.dev.models.MyOfferModel;
import com.dev.models.MyProductsModel;
import lombok.Data;

import java.util.List;

@Data
public class MyOffersResponse extends BasicResponse{
    private List<MyOfferModel> myOffers;

    public MyOffersResponse(boolean success, Integer errorCode, List<MyOfferModel> myOffers) {
        super(success, errorCode);
        this.myOffers = myOffers;
    }
}
