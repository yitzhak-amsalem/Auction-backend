package com.dev.models;

import com.dev.objects.Offer;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class OfferModel {
    private Integer amountOffer;
    private String offerDate;


    public OfferModel(Offer offer){
        this.amountOffer = offer.getAmount();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.offerDate = simpleDateFormat.format(offer.getDate());
    }
}
