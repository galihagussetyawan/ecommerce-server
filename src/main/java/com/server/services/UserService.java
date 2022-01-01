package com.server.services;

import java.util.List;

import com.server.domain.Role;
import com.server.domain.User;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String string);

    User getUser(String username);

    List<User> getUserByRole(Role role);

    List<User> getUsers();
}