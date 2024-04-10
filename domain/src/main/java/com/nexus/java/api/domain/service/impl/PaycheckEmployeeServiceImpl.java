package com.nexus.java.api.domain.service.impl;

import com.nexus.aws.cloud.S3;
import com.nexus.aws.model.S3File;
import com.nexus.java.api.domain.mapper.application.AllEmployees;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import com.nexus.util.DateUtils;
import com.nexus.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaycheckEmployeeServiceImpl implements PaycheckEmployeeService {

    private final EmployeePersistence employeePersistence;
    private final S3 s3Uploader;

    @Override
    @SneakyThrows(IOException.class)
    public void putFile(MultipartFile file, String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e inserção no Buckets3...");
        FileUtils.validateContentTypeIsApplicationPdf(file.getContentType());
        DateUtils.isValidMonthYearFormat(paycheckDate);
        Employee employee = employeePersistence.findById(userId);
        String employeeName = employee.getName().replace(" ", "-").toLowerCase();
        s3Uploader.putObject(file.getInputStream() ,employeeName, paycheckDate);
        log.info("processo de conexão e inserção PUT no Buckets3 finalizado com sucesso.");
    }

    @Override
    public void createFolder(String name) {
        this.s3Uploader.createFolder(name.replace(" ", "-").toLowerCase());
    }

    @Override
    public List<AllEmployees> findAllUsersWithBasicInfo() {
        return employeePersistence.findAllUsersWithBasicInfo();
    }

    @Override
    @SneakyThrows(IOException.class)
    public void updateFile(MultipartFile file, String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e atualização no Buckets3...");
        DateUtils.isValidMonthYearFormat(paycheckDate);
        FileUtils.validateContentTypeIsApplicationPdf(file.getContentType());
        Employee employee = employeePersistence.findById(userId);
        String employeeName = employee.getName().replace(" ", "-").toLowerCase();
        s3Uploader.updateObject(file.getInputStream(), employeeName, paycheckDate);
        log.info("processo de conexão e atualização no Buckets3 finaizado com sucesso.");
    }

    @Override
    public void deletePaycheckById(String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e delete no Buckets3...");
        Employee employee = employeePersistence.findById(userId);
        s3Uploader.deleteFile(
                employee.getName().toLowerCase().replace(" ", "-"),
                paycheckDate
        );
        log.info("processo de conexão e delete no Buckets3 finalizado com sucesso");
    }

    @Override
    public byte[] getContentFile(String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e busca de arquivos no Buckets3...");
        DateUtils.isValidMonthYearFormat(paycheckDate);
        Employee employee = employeePersistence.findById(userId);
        String employeeName = employee.getName().replace(" ", "-").toLowerCase();
        var file = s3Uploader.getFile(employeeName, paycheckDate);
        log.info("processo de conexão e busca de arquivos no Buckets3 finalizado com sucesso.");
        return file;
    }

    @Override
    public List<S3File> getPaychecksByUserId(String userId) {
        log.info("iniciado o processo de conexão e busca da lista de aruivos no Buckets3...");
        Employee employee = employeePersistence.findById(userId);
         var paycheck = s3Uploader.listObjectsInFolder(employee.getName()
                .toLowerCase()
                .replace(" ", "-")
        );

        log.info("processo de conexão e busca da lista de aruivos no Buckets3 finalizada com sucesso.");
        return paycheck;
    }
}
