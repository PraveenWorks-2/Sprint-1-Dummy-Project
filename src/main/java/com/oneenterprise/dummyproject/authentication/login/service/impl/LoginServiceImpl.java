package com.oneenterprise.dummyproject.authentication.login.service.impl;

import com.oneenterprise.dummyproject.authentication.login.dto.request.LoginRequest;
import com.oneenterprise.dummyproject.authentication.login.dto.response.LoginResponse;
import com.oneenterprise.dummyproject.authentication.login.entity.User;
import com.oneenterprise.dummyproject.authentication.login.repository.AuthenticationUserRepository;
import com.oneenterprise.dummyproject.authentication.login.service.LoginService;
import com.oneenterprise.dummyproject.authentication.loginvalidation.exception.AccountDisabledException;
import com.oneenterprise.dummyproject.authentication.loginvalidation.exception.AccountLockedException;
import com.oneenterprise.dummyproject.authentication.loginvalidation.exception.InvalidCredentialsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.oneenterprise.dummyproject.authentication.jwt.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

public LoginServiceImpl(AuthenticationUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
        		.orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!user.isEnabled()) {
            throw new AccountDisabledException("Account is disabled");
        }
        
        if (user.isAccountLocked()) {
            throw new AccountLockedException("Account is locked due to multiple failed login attempts");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(token, user.getUsername(), "Login successful");
    }
}