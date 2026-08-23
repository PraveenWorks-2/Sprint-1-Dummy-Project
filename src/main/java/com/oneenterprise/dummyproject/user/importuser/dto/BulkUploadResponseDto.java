package com.oneenterprise.dummyproject.user.importuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BulkUploadResponseDto {

    private int totalRecords;
    private int successfulRecords;
    private int failedRecords;
}