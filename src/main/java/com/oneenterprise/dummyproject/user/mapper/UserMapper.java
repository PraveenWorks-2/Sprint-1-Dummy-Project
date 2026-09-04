package com.oneenterprise.dummyproject.user.mapper;

import com.oneenterprise.dummyproject.user.dto.UserRegistrationRequestDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationResponseDto;
import com.oneenterprise.dummyproject.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User mapToUser(UserRegistrationRequestDto requestDto) {

        User user = new User();

        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setTenantId(requestDto.getTenantId());
        user.setDepartmentId(requestDto.getDepartmentId());

        return user;
    }

    public static UserRegistrationResponseDto mapToResponseDto(User user) {

        UserRegistrationResponseDto responseDto =
                new UserRegistrationResponseDto();

        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setTenantId(user.getTenantId());
        responseDto.setDepartmentId(user.getDepartmentId());
        responseDto.setStatus(user.getStatus());
        responseDto.setCreatedAt(user.getCreatedAt());
        responseDto.setCreatedBy(user.getCreatedBy());
        responseDto.setUpdatedAt(user.getUpdatedAt());
        responseDto.setUpdatedBy(user.getUpdatedBy());
        responseDto.setDeleted(user.isDeleted());
        responseDto.setDeletedAt(user.getDeletedAt());
        responseDto.setDeletedBy(user.getDeletedBy());

        return responseDto;
    }
}