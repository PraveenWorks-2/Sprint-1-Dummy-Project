package com.oneenterprise.dummyproject.user.service.impl;

import com.oneenterprise.dummyproject.user.dto.UserProfileUpdateDto;
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

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserRegistrationResponseDto registerUser(
            UserRegistrationRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User already exists with email: "
                            + requestDto.getEmail());
        }

        User user = UserMapper.mapToUser(requestDto);

        LocalDate today = LocalDate.now();

        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(today);
        user.setCreatedBy("SYSTEM");
        user.setUpdatedAt(today);
        user.setUpdatedBy("SYSTEM");
        user.setDeleted(false);

        User savedUser = userRepository.save(user);

        return UserMapper.mapToResponseDto(savedUser);
    }

    @Override
    public UserRegistrationResponseDto getUserById(UUID id) {

        User user = userRepository.findById(id)
                .filter(existingUser -> !existingUser.isDeleted())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        return UserMapper.mapToResponseDto(user);
    }

    @Override
    public UserRegistrationResponseDto updateProfile(
            UUID id,
            UserProfileUpdateDto requestDto) {

        User user = userRepository.findById(id)
                .filter(existingUser -> !existingUser.isDeleted())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        // Check whether the new email is already used
        // by another user
        if (!user.getEmail().equalsIgnoreCase(requestDto.getEmail())
                && userRepository.existsByEmail(requestDto.getEmail())) {

            throw new UserAlreadyExistsException(
                    "User already exists with email: "
                            + requestDto.getEmail());
        }

        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());

        user.setUpdatedAt(LocalDate.now());
        user.setUpdatedBy("SYSTEM");

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToResponseDto(updatedUser);
    }

    @Override
    public UserRegistrationResponseDto activateUser(UUID id) {

        User user = userRepository.findById(id)
                .filter(existingUser -> !existingUser.isDeleted())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        user.setStatus(UserStatus.ACTIVE);
        user.setUpdatedAt(LocalDate.now());
        user.setUpdatedBy("SYSTEM");

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToResponseDto(updatedUser);
    }

    @Override
    public UserRegistrationResponseDto deactivateUser(UUID id) {

        User user = userRepository.findById(id)
                .filter(existingUser -> !existingUser.isDeleted())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id));

        user.setStatus(UserStatus.INACTIVE);
        user.setUpdatedAt(LocalDate.now());
        user.setUpdatedBy("SYSTEM");

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToResponseDto(updatedUser);
    }
}