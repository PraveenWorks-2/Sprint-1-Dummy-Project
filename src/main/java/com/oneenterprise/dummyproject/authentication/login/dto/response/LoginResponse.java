package com.oneenterprise.dummyproject.authentication.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String tokenType = "Bearer";
    private String username;
    private String message;

    public LoginResponse(String token, String username, String message) {
        this.token = token;
        this.username = username;
        this.message = message;
        this.tokenType = "Bearer";
    }
}