package mocks;


import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.mapper.infrastructure.enums.Profession;

public class EmployeeDbPrincipalMock {

    public static EmployeeDbPrincipal get(){
        return EmployeeDbPrincipal.builder()
                .code(123)
                .name("Teste")
                .profession(Profession.ACOUGUEIRO)
                .build();
    }
}
