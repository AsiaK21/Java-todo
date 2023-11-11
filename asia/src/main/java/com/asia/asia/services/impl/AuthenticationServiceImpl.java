package com.asia.asia.services.impl;

import com.asia.asia.dao.request.SignInRequest;
import com.asia.asia.dao.request.SignUpRequest;
import com.asia.asia.dao.response.JwtAuthenticationResponse;
import com.asia.asia.entities.Role;
import com.asia.asia.entities.User;
import com.asia.asia.repositories.UserRepository;
import com.asia.asia.services.AuthenticationService;
import com.asia.asia.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).username(request.getUsername())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        var user = userRepository.findByUsername(request.getUsername());
        //TODO:
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            var jwt = jwtService.generateToken(user.get());
            return JwtAuthenticationResponse.builder().token(jwt).build();
        }
        return new JwtAuthenticationResponse();
    }
}
