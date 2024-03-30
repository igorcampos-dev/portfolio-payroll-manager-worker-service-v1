package com.nexus.contracheque.mocks;

import com.nexus.contracheque.model.dto.EmployeeBaseMaster;
import com.nexus.contracheque.model.entity.enums.Profession;

public class EmployeeBaseMasterMock {

    public static EmployeeBaseMaster get(){
        return EmployeeBaseMaster.builder()
                .code(123)
                .name("Teste")
                .profession(Profession.ACOUGUEIRO)
                .build();
    }
}
