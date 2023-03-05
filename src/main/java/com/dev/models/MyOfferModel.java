package com.dev.models;

import com.dev.objects.Offer;
import com.dev.utils.Persist;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
public class MyOfferModel {
    private Integer productID;
    private String productName;
    private Integer amountOffer;
    private Boolean isOpen;
    private Boolean isWin;

    public MyOfferModel(Offer offer, List<Offer> offers){
        this.productID = offer.getAuction().getProductObj().getId();
        this.productName = offer.getAuction().getProductObj().getName();
        this.amountOffer = offer.getAmount();
        this.isOpen = offer.getAuction().getIsOpen();
        this.isWin = checkWinOffer(offer, offers);
    }

    private Boolean checkWinOffer(Offer offer, List<Offer> offers) {
        return this.isOpen ?
                null
                :
                offer.getAuction().getWinnerOffer(offers).equals(offer);
    }

}
