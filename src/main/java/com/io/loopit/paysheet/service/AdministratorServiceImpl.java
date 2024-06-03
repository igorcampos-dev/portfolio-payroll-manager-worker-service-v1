package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.request.AdministratorLoginDto;
import com.io.loopit.paysheet.controller.dto.response.AdministratorResponse;
import com.io.loopit.paysheet.controller.util.ValidatePasswordUtils;
import com.io.loopit.paysheet.model.EmployeeEntity;
import com.io.loopit.paysheet.model.enums.UserRole;
import com.io.loopit.paysheet.repository.EmployeeRepository;
import com.io.loopit.paysheet.security.util.JwtUtil;
import com.io.loopit.paysheet.service.utils.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;

    @Override
    public AdministratorResponse login(AdministratorLoginDto administratorLoginDto) {
        ValidatePasswordUtils.validPass(administratorLoginDto.getPassword());
        EmployeeEntity employee = employeeRepository.findByCpfOrElseThrow(administratorLoginDto.getCpf());
        this.isNotAdmin(employee);

        return AdministratorResponse.builder()
                .name(employee.getName())
                .token(jwtUtil.encode(employee))
                .build();
    }


    private void isNotAdmin(EmployeeEntity employee){
        boolean condition = employee.getRole() != UserRole.ADMIN;
        Objects.throwIfTrue(condition, new AccessDeniedException("Usuário não tem permissão para acessar este recurso, apenas administradores são permitidos."));
    }

}
