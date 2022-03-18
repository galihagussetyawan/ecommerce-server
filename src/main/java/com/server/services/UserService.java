package com.server.services;

import java.util.List;

import com.server.domain.Role;
import com.server.domain.User;
import com.server.domain.DetailContact;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String string);

    User getUser(String username);

    List<User> getAllUsers();

    User getUserById(int id);

    List<User> getUserByRole(Role role);

    void addUserDetail(User user, DetailContact userDetail);
}