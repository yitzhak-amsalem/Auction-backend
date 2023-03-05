package com.dev.responses;

import com.dev.models.AuctionModel;
import com.dev.objects.Auction;
import com.dev.objects.Offer;
import lombok.Data;

import java.util.List;
@Data
public class AuctionResponse extends BasicResponse{
    private AuctionModel auction;

    public AuctionResponse (Auction auction, List<Offer> offers, String token) {
        this.setSuccess(true);
        System.out.println(offers);
        System.out.println(token);
        this.auction = new AuctionModel(auction, offers, token);
        System.out.println(this.auction);
    }

}
