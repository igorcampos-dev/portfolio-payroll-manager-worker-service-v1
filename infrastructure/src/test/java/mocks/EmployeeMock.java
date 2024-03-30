package mocks;

import com.nexus.java.api.domain.mapper.application.AllEmployees;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import com.nexus.java.api.infrastructure.entity.enums.Profession;
import com.nexus.java.api.infrastructure.entity.enums.UserRole;
import java.util.List;

public class EmployeeMock {

    public static EmployeeEntity get(){
        return EmployeeEntity.builder()
                .name("Joao")
                .code(123)
                .role(UserRole.USER)
                .id("d5c40e61-e91f-45a1-97b7-3925a9f28b78")
                .profession(Profession.ATENDENTE_DE_BALCAO)
                .build();
    }

    public static EmployeeEntity get2(){
        return EmployeeEntity.builder()
                .password("password")
                .role(UserRole.USER)
                .cpf("678.793.790-37")
                .build();
    }

    public static EmployeeEntity get3(){
        return EmployeeEntity.builder()
                .name("Igor")
                .password("password")
                .role(UserRole.ADMIN)
                .cpf("678.793.790-37")
                .build();
    }

    public static Employee get4(){
        return Employee.builder()
                .name("Joao")
                .code(123)
                .role(com.nexus.java.api.domain.mapper.infrastructure.enums.UserRole.USER)
                .id("d5c40e61-e91f-45a1-97b7-3925a9f28b78")
                .profession(com.nexus.java.api.domain.mapper.infrastructure.enums.Profession.ATENDENTE_DE_BALCAO)
                .build();
    }

    public static Employee get5(){
        return Employee.builder()
                .name("Joao")
                .code(123)
                .role(com.nexus.java.api.domain.mapper.infrastructure.enums.UserRole.ADMIN)
                .id("d5c40e61-e91f-45a1-97b7-3925a9f28b78")
                .profession(com.nexus.java.api.domain.mapper.infrastructure.enums.Profession.ATENDENTE_DE_BALCAO)
                .build();
    }

    public static List<AllEmployees> list2(){
        return List.of(
                AllEmployees.builder()
                        .name("igor")
                        .build(),

                AllEmployees.builder()
                        .name("kaue")
                        .build()
        );
    }


}
