package com.asia.asia;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityConfiguration {
    void configure(HttpSecurity http) throws Exception;
}
