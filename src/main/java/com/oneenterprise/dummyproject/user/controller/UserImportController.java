package com.oneenterprise.dummyproject.user.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.oneenterprise.dummyproject.user.dto.BulkUploadResponseDto;
import com.oneenterprise.dummyproject.user.dto.BulkUserRequestDto;
import com.oneenterprise.dummyproject.user.dto.ImportSummaryDto;
import com.oneenterprise.dummyproject.user.importuser.service.UserImportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserImportController {

    private final UserImportService userImportService;

    @PostMapping("/bulk-upload")
    public ResponseEntity<BulkUploadResponseDto> bulkUpload(
            @RequestBody List<BulkUserRequestDto> users) {

        return ResponseEntity.ok(userImportService.bulkUpload(users));
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportSummaryDto> importUsers(
            @RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(userImportService.importCsv(file));
    }
}