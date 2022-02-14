package com.server.services.impl;

import java.util.List;

import com.server.domain.ShippingAddress;
import com.server.domain.UserShipping;
import com.server.repository.ShippingAddressRepository;
import com.server.services.ShippingAddressService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    @Override
    public ShippingAddress creataShippingAddress(
            String shippingAddressName,
            String ShippingAddressStreet1,
            String ShippingAddressStreet2,
            String ShippingAddressCity,
            String ShippingAddressState,
            String ShippingAddressCountry,
            String ShippingAddressZipCode) {

        ShippingAddress shippingAddress = ShippingAddress.builder()
                .shippingAddressName(shippingAddressName)
                .shippingAddressStreet1(ShippingAddressStreet1)
                .shippingAddressStreet2(ShippingAddressStreet2)
                .shippingAddressCity(ShippingAddressCity)
                .shippingAddressState(ShippingAddressState)
                .shippingAddressCountry(ShippingAddressCountry)
                .shippingAddressZipCode(ShippingAddressZipCode)
                .build();

        return shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public ShippingAddress setByUserShipping(UserShipping userShipping) {

        ShippingAddress shippingAddress = new ShippingAddress();

        shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
        shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
        shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
        shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
        shippingAddress.setShippingAddressState(userShipping.getUserShippingState());
        shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
        shippingAddress.setShippingAddressZipCode(userShipping.getUserShippingZipCode());

        return shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public ShippingAddress getByShippingAddressName(String shippingAddressName) {
        return shippingAddressRepository.findByShippingAddressName(shippingAddressName);
    }
}