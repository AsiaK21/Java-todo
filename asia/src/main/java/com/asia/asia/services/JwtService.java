package com.asia.asia.services;

import com.asia.asia.entities.Role;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    Long extractLoggedInUserId();
    Role extractLoggedInUserRole();
}
