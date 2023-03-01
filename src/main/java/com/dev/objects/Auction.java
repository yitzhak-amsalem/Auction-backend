package com.dev.objects;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
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
    private Product product;
    @ManyToOne
    @JoinColumn
    private User publisher; // ? - product.getOwner();
    @OneToMany
    @JoinColumn
    private List<Offer> offers;
}
