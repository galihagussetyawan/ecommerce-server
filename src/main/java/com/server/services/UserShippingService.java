package com.server.services;

import java.util.Optional;

import com.server.domain.User;
import com.server.domain.UserShipping;

public interface UserShippingService {
        Optional<UserShipping> getById(Long id);

        UserShipping getByUser(User user);

        void deteteById(Long id);

        void addUserShipping(
                        User user,
                        String userShippingStreet1,
                        String userShippingStreet2,
                        String userShippingCity,
                        String userShippingState,
                        String userShippingCountry,
                        String userShippingZipCode,
                        boolean userShippingDefault);

        UserShipping updateUserShipping(
                        Long id,
                        String userShippingStreet1,
                        String userShippingStreet2,
                        String userShippingCity,
                        String userShippingState,
                        String userShippingCountry,
                        String userShippingZipCode,
                        boolean userShippingDefault);
}