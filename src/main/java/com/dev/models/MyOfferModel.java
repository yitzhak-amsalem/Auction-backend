package com.dev.models;

import com.dev.objects.Offer;
import com.dev.utils.Persist;
import lombok.Data;

@Data
public class MyOfferModel {
    private Integer productID;
    private String productName;
    private Integer amountOffer;
    private Boolean isOpen;
    private Boolean isWin;

    public MyOfferModel(Offer offer, Persist persist){
        this.productID = offer.getAuction().getProductsObj().getId();
        this.productName = offer.getAuction().getProductsObj().getName();
        this.amountOffer = offer.getAmount();
        this.isOpen = offer.getAuction().getIsOpen();
        this.isWin = checkWinOffer(offer, persist);
    }

    private Boolean checkWinOffer(Offer offer, Persist persist) {
        return this.isOpen ?
                null
                :
                offer.getAuction().getWinnerOffer(persist).equals(offer);
    }

}
