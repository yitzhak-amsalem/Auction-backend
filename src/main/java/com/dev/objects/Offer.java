package com.dev.objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer implements Comparable<Offer>{
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

        public Offer(Integer amount, User offers, Auction auction) {
                this.amount = amount;
                this.date = new Date();
                this.offers = offers;
                this.auction = auction;
        }

        public Offer() {

        }

        @Override
        public int compareTo(Offer o) {
                return ((o.getAmount()) - (this.getAmount()) != 0) ?
                        (this.getAmount()) - (o.getAmount())
                        :
                        o.getDate().compareTo(this.getDate());
        }
}
