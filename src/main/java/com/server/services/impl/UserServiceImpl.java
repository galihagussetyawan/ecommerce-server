package com.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.server.domain.Role;
import com.server.domain.User;
import com.server.domain.DetailContact;
import com.server.repository.RoleRepository;
import com.server.repository.UserDetailRepository;
import com.server.repository.UserRepository;
import com.server.services.UserService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailRepository userDetailRepository;

    @Override
    public User getUserById(int id) {
        return userRepository.findById((long) id).orElse(null);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public List<User> getUserByRole(Role role) {
        return userRepository.findByRoles(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("Username not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @Override
    public void addUserDetail(User user, DetailContact userDetailBody) {

        DetailContact userDetail = DetailContact.builder()
                .firstname(userDetailBody.getFirstname())
                .lastname(userDetailBody.getLastname())
                .birth(userDetailBody.getBirth())
                .address1(userDetailBody.getAddress1())
                .address2(userDetailBody.getAddress2())
                .city(userDetailBody.getCity())
                .state(userDetailBody.getState())
                .country(userDetailBody.getCountry())
                .phone(userDetailBody.getPhone())
                .email(userDetailBody.getEmail())
                .build();

        userDetailRepository.save(userDetail);
        user.setDetailContact(userDetail);
        userRepository.save(user);
    }
}