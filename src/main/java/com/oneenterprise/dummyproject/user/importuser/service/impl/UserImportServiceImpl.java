package com.oneenterprise.dummyproject.user.importuser.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oneenterprise.dummyproject.user.entity.User;
import com.oneenterprise.dummyproject.user.enums.UserStatus;
import com.oneenterprise.dummyproject.user.importuser.dto.BulkUploadResponseDto;
import com.oneenterprise.dummyproject.user.importuser.dto.BulkUserRequestDto;
import com.oneenterprise.dummyproject.user.importuser.dto.ImportErrorDto;
import com.oneenterprise.dummyproject.user.importuser.dto.ImportSummaryDto;
import com.oneenterprise.dummyproject.user.importuser.service.UserImportService;
import com.oneenterprise.dummyproject.user.repository.UserRepository;

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
            user.setPhone(dto.getPhone());

            user.setPassword("Temp@123");
            user.setStatus(UserStatus.ACTIVE);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

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

            if (data.length < 4) {
                errors.add(new ImportErrorDto(total, "", "Invalid CSV row"));
                continue;
            }

            String firstName = data[0].trim();
            String lastName = data[1].trim();
            String email = data[2].trim();
            String phone = data[3].trim();

            if (userRepository.existsByEmail(email)) {
                errors.add(new ImportErrorDto(total, email, "Email already exists"));
                continue;
            }

            User user = new User();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhone(phone);

            user.setPassword("Temp@123");
            user.setStatus(UserStatus.ACTIVE);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            userRepository.save(user);
            success++;
        }

        ImportSummaryDto summary = new ImportSummaryDto();

        summary.setTotalRecords(total);
        summary.setSuccessfulRecords(success);
        summary.setFailedRecords(errors.size());
        summary.setErrors(errors);

        return summary;
    }
}