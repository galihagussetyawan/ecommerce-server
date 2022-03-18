package com.server.services.impl;

import com.server.domain.Store;
import com.server.domain.User;
import com.server.repository.StoreRepository;
import com.server.services.StoreService;
import com.server.services.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public Store getStoreByUser(User user) {
        return storeRepository.findByUser(user);
    }

    @Override
    public void createStore(User user, Store storeRequest) {

        userService.addRoleToUser(user.getUsername(), "SELLER");

        Store store = Store.builder()
                .username(storeRequest.getUsername())
                .name(storeRequest.getName())
                .user(user)
                .build();

        storeRepository.save(store);
    }

}