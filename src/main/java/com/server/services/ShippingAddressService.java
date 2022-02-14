package com.server.services;

import com.server.domain.ShippingAddress;
import com.server.domain.UserShipping;

public interface ShippingAddressService {

    ShippingAddress getByShippingAddressName(String shippingAddressName);

    ShippingAddress creataShippingAddress(
            String shippingAddressName,
            String ShippingAddressStreet1,
            String ShippingAddressStreet2,
            String ShippingAddressCity,
            String ShippingAddressState,
            String ShippingAddressCountry,
            String ShippingAddressZipCode);

    ShippingAddress setByUserShipping(UserShipping userShipping);
}