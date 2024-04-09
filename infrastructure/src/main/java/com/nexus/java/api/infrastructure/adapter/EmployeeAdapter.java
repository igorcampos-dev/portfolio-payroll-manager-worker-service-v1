package com.nexus.java.api.infrastructure.adapter;

import com.nexus.java.api.domain.mapper.application.AllEmployees;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.model.RegisterEmployeeModel;
import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.infrastructure.Mapper.EmployeeMapper;
import com.nexus.java.api.infrastructure.repository.PrincipalRepository;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeAdapter implements EmployeePersistence {
    private final EmployeeValidationRepository employeeValidationRepository;
    private final PrincipalRepository principalRepository;

    @Override
    public EmployeeDbPrincipal findDescriptionByCpf(String cpf) {
        log.info("iniciando processo de busca de informações de registro de um funcionário pelo id...");
        var result = principalRepository.findDescriptionByCpf(cpf);
        log.info("processo de busca de informações de registro finalizado com sucesso.");
        return result ;
    }

    @Override
    public Employee findByCpf(String cpf) {
        log.info("iniciando processo de busca de informações de um funcionário pelo id...");
        var employee = employeeValidationRepository.findByCpf(cpf);
        log.info("processo de busca de informações de um funcionário finalizado com sucesso.");
        return EmployeeMapper.toDomain(employee);
    }

    @Override
    public void userExistsByCpf(String cpf) {
        log.info("iniciando o processo de conexão para verificação da existência de um funcionário no banco de dados...");
        employeeValidationRepository.userExistsByCpf(cpf);
        log.info("processo de verificação finalizada com sucesso.");
    }

    @Override
    public void save(EmployeeDbPrincipal employeeProperties, RegisterEmployeeModel registerEmployeeModel) {
        log.info("iniciando processo de salvar um funcionário no banco de dados");
        employeeValidationRepository.save(EmployeeMapper.PrincipalToEntity(employeeProperties, registerEmployeeModel));
        log.info("processo de salvamento finalizado com sucesso");
    }

    @Override
    public Employee findById(String userId) {
        log.info("iniciando processo de busca de dados de um funcionário por id ");
        var employee = employeeValidationRepository.findById(userId);
        log.info("processo de busca de dados finalizado com sucesso.");
        return EmployeeMapper.toDomain(employee);
    }

    @Override
    public List<AllEmployees> findAllUsersWithBasicInfo() {
        log.info("iniciando processo de busca de informações basicas");
        var employees = employeeValidationRepository.findAllUsersWithBasicInfo();
        log.info("processo de busca de informações finalizada com sucesso.");
        return employees
                .stream()
                .map(user -> new AllEmployees(user.getId(), user.getName(), user.getProfession().name()))
                .toList();
    }
}
