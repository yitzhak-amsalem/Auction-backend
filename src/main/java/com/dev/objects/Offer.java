package com.dev.objects;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Data
@Entity
@Table
public class Offer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        private int id;
        @Column
        private Integer amount;
        @Column
        private Date date;
        @ManyToOne
        @JoinColumn
        private User offers;
        @ToString.Exclude
        @ManyToOne
        @JoinColumn
        private Auction auction;

}
