package com.oneenterprise.dummyproject.user.dto;

import java.util.List;

import lombok.Data;

@Data
public class ImportSummaryDto {

    private int totalRecords;
    private int successfulRecords;
    private int failedRecords;
    private List<ImportErrorDto> errors;
}