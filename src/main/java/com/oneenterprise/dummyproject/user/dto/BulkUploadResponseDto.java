package com.oneenterprise.dummyproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BulkUploadResponseDto {

    private int totalRecords;
    private int successfulRecords;
    private int failedRecords;
}