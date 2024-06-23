package com.io.loopit.paysheet.config.runner;

import com.io.loopit.paysheet.model.EmployeeEntity;
import com.io.loopit.paysheet.model.enums.Profession;
import com.io.loopit.paysheet.model.enums.UserRole;
import com.io.loopit.paysheet.repository.EmployeeRepository;
import com.nexus.aws.cloud.S3;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class Run implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final S3 s3;
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        createEmployeeIfNotFound("05605662040", UserRole.USER, Profession.REPOSITOR, "Igor de campos", 993302);
        createEmployeeIfNotFound("58892239090", UserRole.ADMIN, Profession.GERENTE_DE_LOJA, "admin", 993303);
    }

    private void createEmployeeIfNotFound(String cpf, UserRole role, Profession profession, String name, Integer code) {
        if (employeeRepository.findByCpf(cpf).isEmpty()) {
            EmployeeEntity employee = EmployeeEntity.builder()
                    .cpf(cpf)
                    .code(code)
                    .role(role)
                    .profession(profession)
                    .name(name)
                    .password(PASSWORD_ENCODER.encode("23097AaQs2"))
                    .build();

            s3.createFolder(employee.getName().toLowerCase().replace(" ", "-"));
            employeeRepository.save(employee);
            log.info("Created user with credentials: {}", employee);
        }
    }

}