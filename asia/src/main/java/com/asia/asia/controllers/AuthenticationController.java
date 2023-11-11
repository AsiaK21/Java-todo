package com.asia.asia.controllers;

import com.asia.asia.dao.request.SignInRequest;
import com.asia.asia.dao.request.SignUpRequest;
import com.asia.asia.dao.response.JwtAuthenticationResponse;
import com.asia.asia.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/signup")
    public ModelAndView signupView() {
        ModelAndView modelAndView = new ModelAndView("sign-up");
        modelAndView.addObject("register", new SignUpRequest());
        return modelAndView;
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(SignUpRequest request) {
        // TODO: Zapisać ten token co zostanie wygenerowny w cookie i zwrócić na front
        // TODO: W przypdaku poprawnego zarejestrowania przejść na strone z taskami w przeciwnym wypadku spowrotem redirect na /signup (ten po get)
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @GetMapping("/signin")
    public ModelAndView signinView() {
        ModelAndView modelAndView = new ModelAndView("sign-in");
        modelAndView.addObject("login", new SignInRequest());
        return modelAndView;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin( SignInRequest request) {
        // TODO: Zapisać ten token co zostanie wygenerowny w cookie i zwrócić na front
        // TODO: W przypdaku poprawnego zarejestrowania przejść na strone z taskami w przeciwnym wypadku spowrotem redirect na /signup (ten po get)
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
