package com.nexus.contracheque.mocks;

import com.nexus.contracheque.model.dto.AllEmployeesDTO;
import com.nexus.contracheque.model.entity.Employee;
import com.nexus.contracheque.model.entity.enums.Profession;
import com.nexus.contracheque.model.entity.enums.UserRole;

import java.util.List;

public class EmployeeMock {

    public static Employee get(){
        return Employee.builder()
                .name("Joao")
                .code(123)
                .role(UserRole.USER)
                .id("d5c40e61-e91f-45a1-97b7-3925a9f28b78")
                .profession(Profession.ATENDENTE_DE_BALCAO)
                .build();
    }

    public static Employee get2(){
        return Employee.builder()
                .password("password")
                .role(UserRole.USER)
                .cpf("678.793.790-37")
                .build();
    }

    public static Employee get3(){
        return Employee.builder()
                .name("Igor")
                .password("password")
                .role(UserRole.ADMIN)
                .cpf("678.793.790-37")
                .build();
    }
    
    public static List<AllEmployeesDTO> list(){
        return List.of(
                AllEmployeesDTO.builder()
                        .name("igor")
                        .build(),

                AllEmployeesDTO.builder()
                        .name("kaue")
                        .build()
        );
    }
}
