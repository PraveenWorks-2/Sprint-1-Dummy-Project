package com.oneenterprise.dummyproject.user.service.impl;

import com.oneenterprise.dummyproject.user.dto.UserRegistrationRequestDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationResponseDto;
import com.oneenterprise.dummyproject.user.entity.User;
import com.oneenterprise.dummyproject.user.enums.UserStatus;
import com.oneenterprise.dummyproject.user.exception.UserAlreadyExistsException;
import com.oneenterprise.dummyproject.user.exception.UserNotFoundException;
import com.oneenterprise.dummyproject.user.mapper.UserMapper;
import com.oneenterprise.dummyproject.user.repository.UserRepository;
import com.oneenterprise.dummyproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User already exists with email: " + requestDto.getEmail()
            );
        }
        User user = UserMapper.mapToUser(requestDto);

        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return UserMapper.mapToResponseDto(savedUser);
    }

    @Override
    public UserRegistrationResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return UserMapper.mapToResponseDto(user);
    }

    @Override
    public UserRegistrationResponseDto activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setStatus(UserStatus.ACTIVE);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToResponseDto(updatedUser);
    }

    @Override
    public UserRegistrationResponseDto deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setStatus(UserStatus.INACTIVE);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToResponseDto(updatedUser);
    }
}
