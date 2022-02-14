package com.server.services.impl;

import java.util.Optional;

import com.server.domain.User;
import com.server.domain.UserShipping;
import com.server.repository.UserShippingRepository;
import com.server.services.UserShippingService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserShippingServiceImpl implements UserShippingService {

    private final UserShippingRepository userShippingRepository;

    @Override
    public Optional<UserShipping> getById(Long id) {
        return userShippingRepository.findById(id);
    }

    @Override
    public UserShipping getByUser(User user) {
        return userShippingRepository.findByUser(user);
    }

    @Override
    public void deteteById(Long id) {
        userShippingRepository.deleteById(id);
    }

    @Override
    public void addUserShipping(
            User user,
            String userShippingStreet1,
            String userShippingStreet2,
            String userShippingCity,
            String userShippingState,
            String userShippingCountry,
            String userShippingZipCode,
            boolean userShippingDefault) {

        UserShipping userShipping = UserShipping.builder()
                .user(user)
                .userShippingName(user.getUsername())
                .userShippingStreet1(userShippingStreet1)
                .userShippingStreet2(userShippingStreet2)
                .userShippingCity(userShippingCity)
                .userShippingState(userShippingState)
                .userShippingCountry(userShippingCountry)
                .userShippingZipCode(userShippingZipCode)
                .userShippingDefault(true)
                .build();

        userShippingRepository.save(userShipping);
    }

    @Override
    public UserShipping updateUserShipping(
            Long id,
            String userShippingStreet1,
            String userShippingStreet2,
            String userShippingCity,
            String userShippingState,
            String userShippingCountry,
            String userShippingZipCode,
            boolean userShippingDefault) {

        UserShipping userShipping = userShippingRepository.getById(id);

        if (userShipping == null) {
            throw new IllegalArgumentException("user shipping not found");
        }

        userShipping.setUserShippingStreet1(userShippingStreet1);
        userShipping.setUserShippingStreet2(userShippingStreet2);
        userShipping.setUserShippingCity(userShippingCity);
        userShipping.setUserShippingState(userShippingState);
        userShipping.setUserShippingCountry(userShippingCountry);
        userShipping.setUserShippingZipCode(userShippingZipCode);
        userShipping.setUserShippingDefault(userShippingDefault);

        return userShipping;
    }
}