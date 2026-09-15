package com.oneenterprise.dummyproject.user.controller;

import com.oneenterprise.dummyproject.user.dto.UserProfileUpdateDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationRequestDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationResponseDto;
import com.oneenterprise.dummyproject.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<UserRegistrationResponseDto> registerUser(
            @Valid @RequestBody UserRegistrationRequestDto requestDto) {

        UserRegistrationResponseDto savedResponseDto = userService.registerUser(requestDto);

        return new ResponseEntity<>(savedResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserRegistrationResponseDto> getUserById(
            @PathVariable("id") UUID id) {

        UserRegistrationResponseDto responseDto = userService.getUserById(id);

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("{id}/profile")
    public ResponseEntity<UserRegistrationResponseDto> updateProfile(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UserProfileUpdateDto requestDto) {

        UserRegistrationResponseDto responseDto = userService.updateProfile(id, requestDto);

        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/activate/{id}")
    public ResponseEntity<UserRegistrationResponseDto> activateUser(
            @PathVariable("id") UUID id) {

        UserRegistrationResponseDto responseDto = userService.activateUser(id);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/de-activate/{id}")
    public ResponseEntity<UserRegistrationResponseDto> deactivateUser(
            @PathVariable("id") UUID id) {

        UserRegistrationResponseDto responseDto = userService.deactivateUser(id);

        return ResponseEntity.ok(responseDto);
    }
}