package com.dev.controllers;

import com.dev.models.AuctionModel;
import com.dev.objects.Auction;
import com.dev.objects.Offer;
import com.dev.objects.User;
import com.dev.responses.AllAuctionsResponse;
import com.dev.responses.AuctionResponse;
import com.dev.responses.BasicResponse;

import com.dev.responses.UsernameResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.dev.utils.Errors.ERROR_NO_SUCH_PRODUCT;
import static com.dev.utils.Errors.ERROR_NO_SUCH_TOKEN;

@RestController
public class DashboardController {

    @Autowired
    private Persist persist;
/*
    @Autowired
    private LiveUpdateController liveUpdatesController;
*/

    @RequestMapping(value = "get-all-auctions", method = RequestMethod.GET)
    public BasicResponse getAllAuctions (String token) {
        BasicResponse response;
        User user = persist.getUserByToken(token);
        if (user != null){
            List<Auction> auctions = persist.getAllOpenAuctions();
            List<AuctionModel> allAuctions = auctions.stream()
                    .map(auction -> {
                        List<Offer> auctionOffers = persist.getOffersByAuctionID(auction.getId());
                        return new AuctionModel(auction, auctionOffers, token);
                    }).collect(Collectors.toList());
            response = new AllAuctionsResponse(true, null, allAuctions);
        } else {
            response = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return response;
    }





    // SSEvents
/*    @RequestMapping(value = "send-message", method = RequestMethod.POST)
    public BasicResponse sendMessage (String token, int recipientID, String content) {
        BasicResponse basicResponse = null;
        User user = persist.getUserByToken(token);
        if (user != null){
            User recipient = persist.getUserByID(recipientID);
            if (recipient != null){
                Message message = new Message(user, recipient, content);
                liveUpdatesController.sendConversationMessage(user.getId(), recipientID, message);
                basicResponse = new BasicResponse(true, null);
            } else {
                basicResponse = new BasicResponse(false, ERROR_NO_SUCH_RECIPIENT);
            }
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return basicResponse;

    }

    @RequestMapping (value = "/start-typing", method = RequestMethod.POST)
    public BasicResponse startTyping (String token, int recipientID) {
        BasicResponse basicResponse = null;
        User user = persist.getUserByToken(token);
        if (user != null) {
            liveUpdatesController.sendStartTypingEvent(user.getId(), recipientID);
            basicResponse = new BasicResponse(true, null);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return basicResponse;
    }*/
}
