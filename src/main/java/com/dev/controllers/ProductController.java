package com.dev.controllers;

import com.dev.objects.Auction;
import com.dev.objects.Offer;
import com.dev.objects.User;
import com.dev.responses.AuctionResponse;
import com.dev.responses.BasicResponse;
import com.dev.responses.UserDetailsResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.dev.utils.Constants.OFFER_COST;
import static com.dev.utils.Errors.*;

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
        User user = persist.getUserByToken(token);
        if (user != null){
            Auction auction = persist.getAuctionByProductID(productID);
            if (auction != null){
                List<Offer> auctionOffers = persist.getOffersByAuctionID(auction.getId());
                response = new AuctionResponse(auction, auctionOffers, token);
            } else {
                response = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
            }
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }
    @RequestMapping(value = "/make-an-offer", method = {RequestMethod.POST, RequestMethod.GET})
    public BasicResponse makeOffer(String token, int amount, int productID) { //todo is admin
        BasicResponse response;
        User user = persist.getUserByToken(token);
        if (user != null){
            Double credit = user.getCredit();
            Auction auction = persist.getAuctionByProductID(productID);
            if (auction != null){
                if(auction.getIsOpen()){
                    if (amount >= auction.getProductObj().getPrice()){
                        Optional<Integer> lastAmount = persist.getOffersByAuctionID(auction.getId()).stream()
                                .filter(offer -> offer.getOffers().equals(user))
                                .map(Offer::getAmount).max(Integer::compareTo);
                        if (lastAmount.isPresent()){
                            if (lastAmount.get() < (double)amount){
                                credit = credit + lastAmount.get();
                            } else {
                                return new BasicResponse(false, ERROR_LESS_OFFER);
                            }
                        }
                        credit = credit - amount;
                        if ((credit - OFFER_COST >= 0)){
                            persist.makeNewOffer(token, amount, auction, credit - OFFER_COST);
                            response = new BasicResponse(true, null);
                        } else {
                            response = new BasicResponse(false, ERROR_NO_CREDIT);
                        }
                    } else {
                        response = new BasicResponse(false, ERROR_AMOUNT_LOWER_THAN_PRICE);
                    }
                } else {
                    response = new BasicResponse(false, ERROR_AUCTION_CLOSED);
                }
            } else {
                response = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
            }
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }
    @RequestMapping(value = "/get-user-details", method = {RequestMethod.GET})
    public BasicResponse getUserDetails(String token) {
        BasicResponse response;
        User user = persist.getUserByToken(token);
        if (user != null){
            response = new UserDetailsResponse(true, null, user);
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }
    @RequestMapping(value = "/close-auction", method = {RequestMethod.POST, RequestMethod.GET})
    public BasicResponse closeAuction(String token, int productID) {
        BasicResponse response;
        User user = persist.getUserByToken(token);
        if (user != null){
            Auction auction = persist.getAuctionByProductID(productID);
            if (auction != null){
                boolean isOwner = persist.checkOwnerOfAuctionByUserID(user.getId(), productID);




                response = new BasicResponse(true, ERROR_NO_SUCH_PRODUCT);
            } else {
                response = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
            }
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }

        return response;
    }
}




