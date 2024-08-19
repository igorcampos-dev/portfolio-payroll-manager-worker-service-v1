package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.response.AllEmployeesResponse;
import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import com.io.loopit.paysheet.repository.payroll.EmployeeRepository;
import com.io.loopit.paysheet.util.ValidationsUtils;
import com.nexus.aws.cloud.S3;
import com.nexus.aws.model.S3File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class PaycheckEmployeeServiceImpl implements PaycheckEmployeeService {

    private final ValidationsUtils validationsUtils;
    private final EmployeeRepository employeeRepository;
    private final S3 s3Uploader;

    @Override
    public void putFile(MultipartFile file, String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e inserção no Buckets3...");
        validationsUtils.validateUUID(userId);
        validationsUtils.validateMonthYearFormat(paycheckDate);
        validationsUtils.validateContentType(file.getContentType());
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        s3Uploader.putObject(
                file,
                this.formatName(employee.getName()),
                this.formatFilename(paycheckDate)
        );
        log.info("processo de conexão e inserção PUT no Buckets3 finalizado com sucesso.");
    }


    @Override
    public List<AllEmployeesResponse> findAllUsersWithBasicInfo() {
        log.info("iniciando processo de busca de informações basicas");
        List<EmployeeEntity> employees = employeeRepository.findAllOrElseThrow();
        log.info("processo de busca de informações finalizada com sucesso.");
        return employees.stream()
                        .map(user ->
                                AllEmployeesResponse.builder()
                                                    .id(user.getId())
                                                    .profession(user.getProfession().name())
                                                    .name(user.getName())
                                                    .build()
                        )
                        .toList();
    }

    @Override
    public void updateFile(MultipartFile file, String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e atualização no Buckets3...");
        validationsUtils.validateUUID(userId);
        validationsUtils.validateMonthYearFormat(paycheckDate);
        validationsUtils.validateContentType(file.getContentType());
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        s3Uploader.updateObject(file, this.formatName(employee.getName()), this.formatFilename(paycheckDate));
        log.info("processo de conexão e atualização no Buckets3 finaizado com sucesso.");
    }

    @Override
    public void deletePaycheckById(String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e delete no Buckets3...");
        validationsUtils.validateUUID(userId);
        validationsUtils.validateMonthYearFormat(paycheckDate);
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        s3Uploader.deleteFile(
                this.formatName(employee.getName()),
                this.formatFilename(paycheckDate)
        );
        log.info("processo de conexão e delete no Buckets3 finalizado com sucesso");
    }

    @Override
    public String getContentFile(String userId, String paycheckDate) {
        log.info("iniciado o processo de conexão e busca de arquivos no Buckets3...");
        validationsUtils.validateUUID(userId);
        validationsUtils.validateMonthYearFormat(paycheckDate);
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        byte[] file = s3Uploader.getFile(
                this.formatName(employee.getName()),
                this.formatFilename(paycheckDate)
        );
        String base64 = Base64.getEncoder().encodeToString(file);
        log.info("processo de conexão e busca de arquivos no Buckets3 finalizado com sucesso.");
        return base64;
    }

    @Override
    public List<S3File> getPaychecksByUserId(String userId) {
        log.info("iniciado o processo de conexão e busca da lista de aruivos no Buckets3...");
        validationsUtils.validateUUID(userId);
        EmployeeEntity employee = employeeRepository.findByIdOrElseThrow(userId);
        var paycheck = s3Uploader.listObjectsInFolder(this.formatName(employee.getName()));
        log.info("processo de conexão e busca da lista de aruivos no Buckets3 finalizada com sucesso.");
        return paycheck;
    }

    private String formatName(String name){
        return name.replace(" ", "-").toLowerCase();
    }

    private String formatFilename(String filename){
        return filename.concat(".pdf");
    }

}
