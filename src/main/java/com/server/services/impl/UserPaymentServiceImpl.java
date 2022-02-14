package com.server.services.impl;

import com.server.domain.UserPayment;
import com.server.repository.UserPaymentRepository;
import com.server.services.UserPaymentService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPaymentServiceImpl implements UserPaymentService {

    private final UserPaymentRepository userPaymentRepository;

    @Override
    public UserPayment getById(Long id) {
        return userPaymentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userPaymentRepository.deleteById(id);
    }

}