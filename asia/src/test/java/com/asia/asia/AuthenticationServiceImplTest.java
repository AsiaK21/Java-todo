package com.asia.asia;

import com.asia.asia.dao.request.SignInRequest;
import com.asia.asia.dao.request.SignUpRequest;
import com.asia.asia.dao.response.JwtAuthenticationResponse;
import com.asia.asia.entities.Role;
import com.asia.asia.entities.User;
import com.asia.asia.repositories.UserRepository;
import com.asia.asia.services.JwtService;
import com.asia.asia.services.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void signup() {
        SignUpRequest signUpRequest = new SignUpRequest("Joanna", "Kozar", "joanna", "asia@onet.pl", "password", "password", "USER");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(jwtService.generateToken(any(User.class))).thenReturn("JwtToken");

        JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);

        assertEquals("JwtToken", response.getToken());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    void signin() {
        SignInRequest signInRequest = new SignInRequest("joanna", "password");

        User user = User.builder()
                .id(1L)
                .username("joanna")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByUsername("joanna")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        JwtAuthenticationResponse response = authenticationService.signin(signInRequest);

        assertEquals("jwtToken", response.getToken());
        verify(userRepository, times(1)).findByUsername("joanna");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
        verify(jwtService, times(1)).generateToken(user);
    }
}
