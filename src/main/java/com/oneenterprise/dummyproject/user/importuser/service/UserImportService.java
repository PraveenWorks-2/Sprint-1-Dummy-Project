package com.oneenterprise.dummyproject.user.importuser.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oneenterprise.dummyproject.user.importuser.dto.BulkUploadResponseDto;
import com.oneenterprise.dummyproject.user.importuser.dto.BulkUserRequestDto;
import com.oneenterprise.dummyproject.user.importuser.dto.ImportSummaryDto;

public interface UserImportService {

    BulkUploadResponseDto bulkUpload(List<BulkUserRequestDto> users);

    ImportSummaryDto importCsv(MultipartFile file) throws IOException;
}