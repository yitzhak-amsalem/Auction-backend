package com.dev.models;

import com.dev.objects.Auction;
import com.dev.objects.Offer;
import lombok.Data;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class AuctionModel {
    private Boolean isOpen;
    private String openingDate;
    private ProductModel productObj;
    private List<OfferModel> myOffers;
    private Integer sumOffers;

    public AuctionModel(Auction auction, List<Offer> offers, String token) {
        this.isOpen = auction.getIsOpen();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.openingDate = simpleDateFormat.format(auction.getOpeningDate());
        this.productObj = new ProductModel(auction.getProductObj());
        this.myOffers = offers.stream()
                .filter(offer -> offer.getOffers().getToken().equals(token))
                .map(OfferModel::new)
                .collect(Collectors.toList());
        this.sumOffers = offers.size();
    }

}
