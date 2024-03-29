package com.nexus.java.api.domain.service.impl;

import com.nexus.aws.cloud.S3;
import com.nexus.aws.model.S3File;
import com.nexus.java.api.domain.exception.InvalidContentTypeException;
import com.nexus.java.api.domain.exception.InvalidPaycheckDateException;
import com.nexus.java.api.domain.mapper.application.AllEmployees;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import com.nexus.utils.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PaycheckEmployeeServiceImpl implements PaycheckEmployeeService {

    private static final String REGEX = "^(0[1-9]|1[0-2])-(19|20)\\d{2}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private final EmployeePersistence employeePersistence;
    private final S3 s3Uploader;

    @Override
    @SneakyThrows(IOException.class)
    public void putFile(MultipartFile file, String userId, String paycheckDate) {
        this.validateContentType(file);
        this.isValidMonthYearFormat(paycheckDate);
        Employee employee = employeePersistence.findById(userId);
        String employeeName = employee.getName().replace(" ", "-").toLowerCase();
        s3Uploader.putObject(file.getInputStream() ,employeeName, paycheckDate);

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
        this.isValidMonthYearFormat(paycheckDate);
        this.validateContentType(file);
        Employee employee = employeePersistence.findById(userId);
        String employeeName = employee.getName().replace(" ", "-").toLowerCase();
        s3Uploader.updateObject(file.getInputStream(), employeeName, paycheckDate);

    }

    @Override
    public void deletePaycheckById(String userId, String paycheckDate) {
        Employee employee = employeePersistence.findById(userId);
        s3Uploader.deleteFile(
                employee.getName().toLowerCase().replace(" ", "-"),
                paycheckDate
        );

    }

    @Override
    public byte[] getContentFile(String userId, String paycheckDate) {
        this.isValidMonthYearFormat(paycheckDate);
        Employee employee = employeePersistence.findById(userId);
        String employeeName = employee.getName().replace(" ", "-").toLowerCase();
        return s3Uploader.getFile(employeeName, paycheckDate);
    }

    @Override
    public List<S3File> getPaychecksByUserId(String userId) {
        Employee employee = employeePersistence.findById(userId);
        return s3Uploader.listObjectsInFolder(employee.getName()
                .toLowerCase()
                .replace(" ", "-")
        );
    }

    private void isValidMonthYearFormat(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw INVALID_PAYCHECK_DATE_EXCEPTION;
        }
    }

    private void validateContentType(MultipartFile file){
        boolean isInvalidContentType = !java.util.Objects.equals(file.getContentType(), "application/pdf");
        Objects.throwIfTrue(isInvalidContentType, INVALID_CONTENT_TYPE_EXCEPTION);
    }

    private static final InvalidContentTypeException INVALID_CONTENT_TYPE_EXCEPTION = new InvalidContentTypeException("Tipo de arquivo inválido, envie apenas arquivos do tipo pdf");
    private static final InvalidPaycheckDateException INVALID_PAYCHECK_DATE_EXCEPTION  = new InvalidPaycheckDateException("Formato inválido de data de contraCheque");
}
