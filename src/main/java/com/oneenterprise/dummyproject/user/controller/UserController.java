package com.oneenterprise.dummyproject.user.controller;

import com.oneenterprise.dummyproject.user.dto.UserRegistrationRequestDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationResponseDto;
import com.oneenterprise.dummyproject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor

public class UserController {

    public final UserService userService;

    @PostMapping ("register")
    private ResponseEntity<UserRegistrationResponseDto> registerUser
            (@Valid @RequestBody UserRegistrationRequestDto requestDto){
        UserRegistrationResponseDto savedResponseDto = userService.registerUser(requestDto);
        return new ResponseEntity<>(savedResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserRegistrationResponseDto> getUserById (@PathVariable("id") Long id){
       UserRegistrationResponseDto userRegistrationResponseDto= userService.getUserById(id);
       return ResponseEntity.ok(userRegistrationResponseDto);
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<UserRegistrationResponseDto> activateUser (@PathVariable("id") Long id){
        UserRegistrationResponseDto userRegistrationResponseDto= userService.activateUser(id);
        return ResponseEntity.ok(userRegistrationResponseDto);
    }

    @GetMapping("/de-activate/{id}")
    public ResponseEntity<UserRegistrationResponseDto> deactivateUser (@PathVariable("id") Long id){
        UserRegistrationResponseDto userRegistrationResponseDto= userService.deactivateUser(id);
        return ResponseEntity.ok(userRegistrationResponseDto);
    }


}
