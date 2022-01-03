package com.server.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String BillingAddressName;
    private String BillingAddressStreet1;
    private String BillingAddressStreet2;
    private String BillingAddressCity;
    private String BillingAddressState;
    private String BillingAddressCountry;
    private String BillingAddressZipcode;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;
}
