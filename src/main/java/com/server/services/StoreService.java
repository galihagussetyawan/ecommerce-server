package com.server.services;

import com.server.domain.Store;
import com.server.domain.User;

public interface StoreService {

    Store getStoreByUser(User user);

    void createStore(User user, Store storeRequest);
}