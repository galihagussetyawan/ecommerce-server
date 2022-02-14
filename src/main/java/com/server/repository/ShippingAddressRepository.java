package com.server.repository;

import com.server.domain.ShippingAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    ShippingAddress findByShippingAddressName(String shippingAddressName);
}
