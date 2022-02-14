package com.server.repository;

import com.server.domain.User;
import com.server.domain.UserShipping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserShippingRepository extends JpaRepository<UserShipping, Long> {
    UserShipping findByUser(User user);
}
