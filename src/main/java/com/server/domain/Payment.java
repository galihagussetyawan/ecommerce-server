package com.server.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;
    private String cardName;
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private int cvc;
    private String holderName;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    private UserBilling userBilling;
}
