package com.asia.asia.services;

import com.asia.asia.dao.request.SignInRequest;
import com.asia.asia.dao.request.SignUpRequest;
import com.asia.asia.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}