package com.asia.asia.dao.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private String username;
    private String password;
}
