package com.dev.controllers;

import com.dev.objects.Auction;
import com.dev.objects.Offer;
import com.dev.objects.User;
import com.dev.responses.AuctionResponse;
import com.dev.responses.BasicResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.dev.utils.Constants.*;
import static com.dev.utils.Errors.*;

@RestController
public class ProductController {
    @Autowired
    private Persist persist;
    @Autowired
    private LiveUpdateController liveUpdateController;
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
            if (!user.isAdmin()) {
                Double credit = user.getCredit();
                Auction auction = persist.getAuctionByProductID(productID);
                if (auction != null){
                    if(auction.getIsOpen()){
                        if (amount >= auction.getProductObj().getPrice()){
                            List<Offer> auctionOffers = persist.getOffersByAuctionID(auction.getId());
                            Optional<Integer> lastAmount = persist.lastOfferAmount(user, auctionOffers);
                            if (lastAmount.isPresent()){
                                if (lastAmount.get() < (double)amount){
                                    credit = credit + lastAmount.get();
                                } else {
                                    return new BasicResponse(false, ERROR_LESS_OFFER);
                                }
                            }
                            credit = credit - amount;
                            if ((credit - OFFER_COST >= 0)){
                                persist.makeNewOffer(user, amount, auction, credit - OFFER_COST);
                                persist.payForSystem(OFFER_COST);
                                liveUpdateController.sendNewOffer(auction.getProductObj().getOwner().getToken());
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
                response = new BasicResponse(false, ERROR_ADMIN_CAN_NOT_OFFER);
            }
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
            if (!user.isAdmin()) {
                Auction auction = persist.getAuctionByProductID(productID);
                if (auction != null){
                    boolean isOwner = persist.checkOwnerOfAuctionByUserID(user.getId(), productID);
                    if (isOwner){
                        if (auction.getIsOpen()){
                            List<Offer> offers = persist.getOffersByAuctionID(auction.getId());
                            if (offers.size() >= MIN_OFFERS_PER_AUCTION){
                                persist.closeAuction(auction);
                                persist.updateCredits(user, auction);
                                liveUpdateController.sendCloseAuction(offers);
                                response = new BasicResponse(true, null);
                            } else {
                                response = new BasicResponse(false, ERROR_LESS_THAN_OFFERS_THRESHOLD);
                            }
                        } else {
                            response = new BasicResponse(false, ERROR_AUCTION_ALREADY_CLOSED);
                        }
                    } else {
                        response = new BasicResponse(false, ERROR_NOT_OWNER);
                    }
                } else {
                    response = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
                }
            } else {
                response = new BasicResponse(false, ERROR_ADMIN_NOT_RELEVANT);
            }
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }
}




