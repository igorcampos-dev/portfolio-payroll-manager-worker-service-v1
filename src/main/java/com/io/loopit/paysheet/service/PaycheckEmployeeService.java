package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.response.AllEmployeesResponse;
import com.nexus.aws.model.S3File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaycheckEmployeeService {
    void putFile(MultipartFile file, String userId, String paycheckDate);
    List<AllEmployeesResponse> findAllUsersWithBasicInfo(int page);
    void updateFile(MultipartFile file, String userId, String paycheckDate);
    void deletePaycheckById(String userId, String paycheckDate);
    String getContentFile(String userId, String paycheckDate);
    List<S3File> getPaychecksByUserId(String userId);
}
