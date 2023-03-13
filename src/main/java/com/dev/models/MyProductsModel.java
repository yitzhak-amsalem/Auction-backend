package com.dev.models;

import com.dev.objects.Auction;
import com.dev.objects.Offer;
import lombok.Data;

import java.util.List;

@Data
public class MyProductsModel {
    private int productID;
    private String productName;
    private Integer maxAmount;
    private Boolean isOpen;

    public MyProductsModel(Auction auction, List<Offer> offers) {
        this.productID = auction.getProductObj().getId();
        this.productName = auction.getProductObj().getName();
        Offer maxOffer = auction.getWinnerOffer(offers);
        this.maxAmount = (maxOffer == null) ? null : maxOffer.getAmount();
        this.isOpen = auction.getIsOpen();
    }
}
