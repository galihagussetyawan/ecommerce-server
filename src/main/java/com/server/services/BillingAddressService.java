package com.server.services;

import com.server.domain.BillingAddress;
import com.server.domain.UserBilling;

public interface BillingAddressService {

    BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
