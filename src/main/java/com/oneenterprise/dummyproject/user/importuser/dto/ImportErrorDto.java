package com.oneenterprise.dummyproject.user.importuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImportErrorDto {

    private int rowNumber;
    private String email;
    private String message;
}