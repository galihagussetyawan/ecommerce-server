package com.server.repository;

import com.server.domain.UserBilling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBillingRepository extends JpaRepository<UserBilling, Long> {

}
