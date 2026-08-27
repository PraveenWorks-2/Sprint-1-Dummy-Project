package com.oneenterprise.dummyproject.authentication.login.service;

import com.oneenterprise.dummyproject.authentication.login.dto.request.LoginRequest;
import com.oneenterprise.dummyproject.authentication.login.dto.response.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest loginRequest);
}