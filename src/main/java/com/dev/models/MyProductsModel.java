package com.dev.models;

import com.dev.objects.Auction;
import com.dev.objects.Offer;
import lombok.Data;

import java.util.List;

@Data
public class MyProductsModel {
    private String productName;
    private Integer maxAmount;
    private Boolean isOpen;

    public MyProductsModel(Auction auction, List<Offer> offers) {
        this.productName = auction.getProductObj().getName();
        this.maxAmount = auction.getWinnerOffer(offers).getAmount();
        this.isOpen = auction.getIsOpen();
    }
}
