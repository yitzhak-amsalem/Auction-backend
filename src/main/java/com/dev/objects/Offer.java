package com.dev.objects;

import lombok.Data;

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
        @ManyToOne
        @Column
        private User offers;
        @Column
        private Integer amount;
        @Column
        private Date date;

}
