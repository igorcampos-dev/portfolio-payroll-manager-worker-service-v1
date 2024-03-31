package com.nexus.java.api.domain.persistence;

import com.nexus.java.api.domain.mapper.application.AllEmployees;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.model.RegisterEmployeeModel;
import java.util.List;

public interface EmployeePersistence {
    EmployeeDbPrincipal findDescriptionByCpf(String cpf);
    Employee findByCpf(String cpf);
    void userExistsByCpf(String cpf);
    void save(EmployeeDbPrincipal employeeProperties, RegisterEmployeeModel registerEmployeeModel);
    Employee findById(String userId);
    List<AllEmployees> findAllUsersWithBasicInfo();
}
