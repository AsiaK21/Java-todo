package com.asia.asia.controllers;

import com.asia.asia.dao.request.SignInRequest;
import com.asia.asia.dao.request.SignUpRequest;
import com.asia.asia.dao.response.JwtAuthenticationResponse;
import com.asia.asia.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/signup")
    public ModelAndView signupView(@ModelAttribute("signupError") String signupError) {
        ModelAndView modelAndView = new ModelAndView("sign-up");
        modelAndView.addObject("register", new SignUpRequest());
        modelAndView.addObject("signupError", signupError);
        return modelAndView;
    }

    @PostMapping("/signup")
    public String signup(SignUpRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if (!request.getPassword().equals(request.getPasswordRepeat())) {
            redirectAttributes.addFlashAttribute("signupError", "Passwords do not match");
            return "redirect:/auth/signup";
        }

        JwtAuthenticationResponse jwtResponse = authenticationService.signup(request);

        if (jwtResponse != null && jwtResponse.getToken() != null) {
            setJwtTokenCookie(response, jwtResponse.getToken());
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("signupError", "Błąd podczas rejestracji.");
            return "redirect:/auth/signup";
        }
    }

    @GetMapping("/signin")
    public ModelAndView signinView(@ModelAttribute("signinError") String signinError) {
        ModelAndView modelAndView = new ModelAndView("sign-in");
        modelAndView.addObject("login", new SignInRequest());
        modelAndView.addObject("signinError", signinError);
        return modelAndView;
    }

    @PostMapping("/signin")
    public String signin(SignInRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        JwtAuthenticationResponse jwtResponse = authenticationService.signin(request);

        if (jwtResponse != null && jwtResponse.getToken() != null) {
            setJwtTokenCookie(response, jwtResponse.getToken());
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("signinError", "Złe dane logownia. Spróbuj ponownie");
            return "redirect:/auth/signin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        setJwtTokenCookie(response, null);
        return "redirect:/auth/signin";
    }

    private void setJwtTokenCookie(HttpServletResponse response, String jwtToken) {
        Cookie cookie = new Cookie("yourJwtCookieName", jwtToken);
        cookie.setMaxAge(jwtToken != null ? 1438 : 0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

