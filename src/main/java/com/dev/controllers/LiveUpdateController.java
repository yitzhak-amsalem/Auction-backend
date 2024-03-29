package com.dev.controllers;

import com.dev.objects.Offer;
import com.dev.objects.User;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.dev.utils.Constants.*;

@Controller
public class LiveUpdateController {
    private final HashMap<String, SseEmitter> emitterMap = new HashMap<>();
    @Autowired
    private Persist persist;

    @RequestMapping(value = "/sse-handler", method = RequestMethod.GET)
    public SseEmitter handle(String token){
        User user = persist.getUserByToken(token);
        SseEmitter sseEmitter = null;
        if (user != null){
            sseEmitter = this.emitterMap.get(token);
            if (sseEmitter == null){
                sseEmitter = new SseEmitter(60L * MINUTE);
                this.emitterMap.put(token, sseEmitter);
            }
        }
        return sseEmitter;
    }

    public void sendCloseAuction(List<Offer> offers) {
        List<String> offersTokens = offers.stream()
                .map(offer -> offer.getOffers().getToken())
                .distinct()
                .collect(Collectors.toList());

        List<SseEmitter> emitters = offersTokens.stream()
                .map(this.emitterMap::get)
                .collect(Collectors.toList());

        emitters.forEach(emitter -> {
            if (emitter != null) {
                try {
                    emitter.send(CLOSE_AUCTION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendNewOffer (String ownerToken) {
        SseEmitter newOfferEmitter = this.emitterMap.get(ownerToken);
        if (newOfferEmitter != null) {
            try {
                newOfferEmitter.send(NEW_OFFER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
