package com.dev.controllers;

import com.dev.objects.Auction;
import com.dev.objects.Offer;
import com.dev.responses.AuctionResponse;
import com.dev.responses.BasicResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.dev.utils.Errors.ERROR_NO_SUCH_PRODUCT;

@RestController
public class ProductController {
    @Autowired
    private Persist persist;
    @PostConstruct
    public void init() {
        persist.initBasicDetails();
    }

    @RequestMapping(value = "/get-auction-by-product", method = {RequestMethod.GET})
    public BasicResponse getAuction(int productID, String token) {
        BasicResponse response;
        Auction auction = persist.getAuctionByProductID(productID);
        if (auction != null){
            List<Offer> auctionOffers = persist.getOffersByAuctionID(auction.getId());
            response = new AuctionResponse(auction, auctionOffers, token);
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
        }
        return response;
    }
    @RequestMapping(value = "/make-an-offer", method = {RequestMethod.POST})
    public BasicResponse makeOffer(String token, int amount, Auction auction) {
        BasicResponse response = new BasicResponse();

        return response;
    }
}
