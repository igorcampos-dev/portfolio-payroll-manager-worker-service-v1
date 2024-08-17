package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.response.AllEmployeesResponse;
import com.io.loopit.paysheet.model.EmployeeEntity;
import com.io.loopit.paysheet.repository.EmployeeRepository;
import com.io.loopit.paysheet.util.DateUtils;
import com.io.loopit.paysheet.util.FileUtils;
import com.nexus.aws.cloud.S3;
import com.nexus.aws.model.S3File;
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
@SuppressWarnings("unused")
public class PaycheckEmployeeServiceImpl implements PaycheckEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final S3 s3Uploader;

    @Override
    @SneakyThrows(IOException.class)
    public void putFile(MultipartFile file, String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e inserção no Buckets3...");

        this.isValidPut(file.getContentType(), paycheckDate);
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        s3Uploader.putObject(file.getInputStream(), this.formatName(employee.getName()), paycheckDate);

        log.info("processo de conexão e inserção PUT no Buckets3 finalizado com sucesso.");
    }

    @Override
    public List<AllEmployeesResponse> findAllUsersWithBasicInfo() {

        log.info("iniciando processo de busca de informações basicas");

        List<EmployeeEntity> employees = employeeRepository.findAllOrElseThrow();

        log.info("processo de busca de informações finalizada com sucesso.");

        return employees
                .stream()
                .map(user -> new AllEmployeesResponse(user.getId(), user.getName(), user.getProfession().name()))
                .toList();
    }

    @Override
    @SneakyThrows(IOException.class)
    public void updateFile(MultipartFile file, String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e atualização no Buckets3...");

        this.isValidPut(file.getContentType(), paycheckDate);
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        s3Uploader.updateObject(file.getInputStream(), this.formatName(employee.getName()), paycheckDate);

        log.info("processo de conexão e atualização no Buckets3 finaizado com sucesso.");
    }

    @Override
    public void deletePaycheckById(String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e delete no Buckets3...");

        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        s3Uploader.deleteFile(
                this.formatName(employee.getName()),
                paycheckDate
        );

        log.info("processo de conexão e delete no Buckets3 finalizado com sucesso");
    }

    @Override
    public byte[] getContentFile(String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e busca de arquivos no Buckets3...");

        DateUtils.isValidMonthYearFormat(paycheckDate);
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        var file = s3Uploader.getFile(this.formatName(employee.getName()), paycheckDate);

        log.info("processo de conexão e busca de arquivos no Buckets3 finalizado com sucesso.");
        return file;
    }

    @Override
    public List<S3File> getPaychecksByUserId(String userId) {
        log.info("iniciado o processo de conexão e busca da lista de aruivos no Buckets3...");

        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        var paycheck = s3Uploader.listObjectsInFolder(this.formatName(employee.getName()));

        log.info("processo de conexão e busca da lista de aruivos no Buckets3 finalizada com sucesso.");
        return paycheck;
    }

    @Override
    public void createFolder(String name) {
        this.s3Uploader.createFolder(this.formatName(name));
    }

    private void isValidPut(String contentType, String paycheckDate){
        FileUtils.isPdf(contentType);
        DateUtils.isValidMonthYearFormat(paycheckDate);
    }

    private String formatName(String name){
        return name.replace(" ", "-").toLowerCase();
    }

}
