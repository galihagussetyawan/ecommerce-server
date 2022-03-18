package com.server.repository;

import com.server.domain.DetailContact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<DetailContact, Long> {

}
