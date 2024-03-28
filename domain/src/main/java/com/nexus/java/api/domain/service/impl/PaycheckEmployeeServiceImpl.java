package com.nexus.java.api.domain.service.impl;

import com.nexus.aws.model.S3File;
import com.nexus.java.api.domain.dto.domain.AllEmployeesDomain;
import com.nexus.java.api.domain.persistence.PaycheckEmployeePersistence;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaycheckEmployeeServiceImpl implements PaycheckEmployeeService {

    private final PaycheckEmployeePersistence employeePersistence;

    @Override
    public void putFile(MultipartFile file, String userId, String paycheckDate) {

    }

    @Override
    public List<AllEmployeesDomain> findAllUsersWithBasicInfo() {
        return null;
    }

    @Override
    public void updateFile(MultipartFile file, String userId, String paycheckDate) {

    }

    @Override
    public void deletePaycheckById(String userId, String paycheckDate) {

    }

    @Override
    public byte[] getContentFile(String userId, String paycheckDate) {
        return new byte[0];
    }

    @Override
    public List<S3File> getPaychecksByUserId(String userId) {
        return null;
    }
}
