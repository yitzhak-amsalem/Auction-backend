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
    @ManyToOne
    @Column
    private User publisher; // ? - product.getOwner();
    @OneToMany
    @Column
    private List<Offer> offers;
    private Date openingDate;
    @ManyToOne
    @Column
    private Product product;
}
