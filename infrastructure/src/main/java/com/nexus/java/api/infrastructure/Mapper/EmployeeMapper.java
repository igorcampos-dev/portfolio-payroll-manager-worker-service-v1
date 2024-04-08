package com.nexus.java.api.infrastructure.Mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.model.RegisterEmployeeModel;
import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import com.nexus.java.api.infrastructure.entity.enums.Profession;
import com.nexus.java.api.infrastructure.entity.enums.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EmployeeMapper {

    private static final ObjectMapper Mapper = new ObjectMapper();
    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static EmployeeEntity PrincipalToEntity(EmployeeDbPrincipal employeeDbPrincipal, RegisterEmployeeModel registerEmployeeModel){
        return EmployeeEntity.builder()
                .code(employeeDbPrincipal.getCode())
                .name(employeeDbPrincipal.getName())
                .profession(Profession.valueOf(employeeDbPrincipal.getProfession().name()))
                .role(UserRole.USER)
                .cpf(registerEmployeeModel.getCpf())
                .password(B_CRYPT_PASSWORD_ENCODER.encode(registerEmployeeModel.getPassword()))
                .build();
    }

    public static Employee toDomain(EmployeeEntity employeeEntity){
        return Mapper.convertValue(employeeEntity, Employee.class);
    }

}
