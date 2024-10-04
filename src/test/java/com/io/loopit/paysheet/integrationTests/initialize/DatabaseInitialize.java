package com.io.loopit.paysheet.integrationTests.initialize;

import com.io.loopit.paysheet.model.enums.Profession;
import com.io.loopit.paysheet.model.enums.UserRole;
import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import com.io.loopit.paysheet.model.rh.EmployeeRhEntity;
import com.io.loopit.paysheet.repository.payroll.EmployeeRepository;
import com.io.loopit.paysheet.repository.rh.RhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class DatabaseInitialize {

    private final EmployeeRepository employeeRepository;
    private final RhRepository rhRepository;

    @PostConstruct
    public void initialize(){
        employeeRepository.save(employee());
        rhRepository.save(employeeRh());
    }


    private EmployeeEntity employee(){
        return EmployeeEntity.builder()
                .id(UUID.randomUUID().toString())
                .role(UserRole.USER)
                .profession(Profession.REPOSITOR)
                .cpf("51927854059")
                .name("TEST EMPLOYEE")
                .password(new BCryptPasswordEncoder().encode("TEST1234567"))
                .build();
    }

    private EmployeeRhEntity employeeRh(){
        return EmployeeRhEntity.builder()
                .id(UUID.randomUUID().toString())
                .name("TEST EMPLOYEE RH")
                .cpf("60512047090")
                .name("TEST EMPLOYEE RH")
                .email("emailTest@gmail.com")
                .phoneNumber("5551995371846")
                .profession(Profession.REPOSITOR.toString())
                .build();
    }

}
