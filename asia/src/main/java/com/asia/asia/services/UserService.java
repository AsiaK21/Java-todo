package com.asia.asia.services;

import com.asia.asia.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();

    Optional<User> getUserById(Long id);
    void assignAdminRole(String username);
}
