package com.dev.objects;

import com.dev.utils.Persist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table (name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private Boolean isOpen;
    @Column
    private Date openingDate;
    @ManyToOne
    @JoinColumn
    private Products productsObj;

    public Auction(Products products) {
        this.isOpen = true;
        this.openingDate = new Date();
        this.productsObj = products;
    }

    public Auction() {

    }
    public Offer getWinnerOffer(Persist persist) {
        return persist.getOffersByAuctionID(this.id).stream()
                .max(Offer::compareTo).get();
    }

/*    public Offer getWinnerOffer(Persist persist) {
        return this.isOpen ?
                null
                :
                persist.getOffersByAuctionID(this.id).size() < 3 ?
                    null
                    :
                    persist.getOffersByAuctionID(this.id).stream()
                    .max(Offer::compareTo).get();
    }*/
}
