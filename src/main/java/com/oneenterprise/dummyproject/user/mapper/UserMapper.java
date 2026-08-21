package com.oneenterprise.dummyproject.user.mapper;

import com.oneenterprise.dummyproject.user.dto.UserRegistrationRequestDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationResponseDto;
import com.oneenterprise.dummyproject.user.entity.User;
import org.springframework.stereotype.Component;

@Component

public class UserMapper {

    public static User mapToUser(UserRegistrationRequestDto requestDto){
        return new User(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getEmail(),
                requestDto.getPhone(),
                requestDto.getPassword()
        );
    }

    public static UserRegistrationResponseDto mapToResponseDto (User user){
        return new UserRegistrationResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


}
