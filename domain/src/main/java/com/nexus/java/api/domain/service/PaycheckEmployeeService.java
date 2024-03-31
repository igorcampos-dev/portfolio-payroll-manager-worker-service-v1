package com.nexus.java.api.domain.service;

import com.nexus.aws.model.S3File;
import com.nexus.java.api.domain.mapper.application.AllEmployees;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface PaycheckEmployeeService {
    void putFile(MultipartFile file, String userId, String paycheckDate);
    void createFolder(String name);
    List<AllEmployees> findAllUsersWithBasicInfo();
    void updateFile(MultipartFile file, String userId, String paycheckDate);
    void deletePaycheckById(String userId, String paycheckDate);
    byte[] getContentFile(String userId, String paycheckDate);
    List<S3File> getPaychecksByUserId(String userId);
}
