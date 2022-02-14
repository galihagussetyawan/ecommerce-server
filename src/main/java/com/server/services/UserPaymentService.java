package com.server.services;

import com.server.domain.UserPayment;

public interface UserPaymentService {
    UserPayment getById(Long id);

    void deleteById(Long id);
}
