package com.oneenterprise.dummyproject.user.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oneenterprise.dummyproject.user.dto.BulkUploadResponseDto;
import com.oneenterprise.dummyproject.user.dto.BulkUserRequestDto;
import com.oneenterprise.dummyproject.user.dto.ImportErrorDto;
import com.oneenterprise.dummyproject.user.dto.ImportSummaryDto;
import com.oneenterprise.dummyproject.user.entity.User;
import com.oneenterprise.dummyproject.user.enums.UserStatus;
import com.oneenterprise.dummyproject.user.repository.UserRepository;
import com.oneenterprise.dummyproject.user.service.UserImportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserImportServiceImpl implements UserImportService {

    private final UserRepository userRepository;

    @Override
    public BulkUploadResponseDto bulkUpload(List<BulkUserRequestDto> users) {

        int success = 0;
        int failed = 0;

        for (BulkUserRequestDto dto : users) {

            if (userRepository.existsByEmail(dto.getEmail())) {
                failed++;
                continue;
            }

            User user = new User();

            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());

            // Required fields from the new users table
            user.setTenantId(dto.getTenantId());
            user.setDepartmentId(dto.getDepartmentId());

            user.setStatus(UserStatus.ACTIVE);

            LocalDate today = LocalDate.now();

            user.setCreatedAt(today);
            user.setCreatedBy("SYSTEM");
            user.setUpdatedAt(today);
            user.setUpdatedBy("SYSTEM");
            user.setDeleted(false);

            userRepository.save(user);
            success++;
        }

        return new BulkUploadResponseDto(users.size(), success, failed);
    }

    @Override
    public ImportSummaryDto importCsv(MultipartFile file) throws IOException {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(file.getInputStream()));

        // Skip CSV header
        reader.readLine();

        List<ImportErrorDto> errors = new ArrayList<>();

        int total = 0;
        int success = 0;

        String line;

        while ((line = reader.readLine()) != null) {

            total++;

            String[] data = line.split(",");

            if (data.length < 5) {
                errors.add(new ImportErrorDto(total, "", "Invalid CSV row"));
                continue;
            }

            String firstName = data[0].trim();
            String lastName = data[1].trim();
            String email = data[2].trim();
            String tenantIdValue = data[3].trim();
            String departmentIdValue = data[4].trim();

            if (userRepository.existsByEmail(email)) {
                errors.add(new ImportErrorDto(total, email, "Email already exists"));
                continue;
            }

            try {
                UUID tenantId = UUID.fromString(tenantIdValue);
                UUID departmentId = UUID.fromString(departmentIdValue);

                User user = new User();

                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setTenantId(tenantId);
                user.setDepartmentId(departmentId);

                user.setStatus(UserStatus.ACTIVE);

                LocalDate today = LocalDate.now();

                user.setCreatedAt(today);
                user.setCreatedBy("SYSTEM");
                user.setUpdatedAt(today);
                user.setUpdatedBy("SYSTEM");
                user.setDeleted(false);

                userRepository.save(user);
                success++;

            } catch (IllegalArgumentException e) {
                errors.add(
                        new ImportErrorDto(
                                total,
                                email,
                                "Invalid tenant ID or department ID"
                        )
                );
            }
        }

        ImportSummaryDto summary = new ImportSummaryDto();

        summary.setTotalRecords(total);
        summary.setSuccessfulRecords(success);
        summary.setFailedRecords(errors.size());
        summary.setErrors(errors);

        return summary;
    }
}