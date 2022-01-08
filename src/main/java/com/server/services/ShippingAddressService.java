package com.server.services;

import com.server.domain.ShippingAddress;
import com.server.domain.UserShipping;

public interface ShippingAddressService {
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}