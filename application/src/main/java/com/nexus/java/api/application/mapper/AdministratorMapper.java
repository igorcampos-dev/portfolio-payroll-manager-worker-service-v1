package com.nexus.java.api.application.mapper;

import com.nexus.java.api.application.dto.request.AdministratorLoginDto;
import com.nexus.java.api.application.dto.response.AdministratorResponse;
import com.nexus.java.api.application.dto.response.AllEmployeesResponse;
import com.nexus.java.api.domain.dto.AdministratorToDomain;
import com.nexus.java.api.domain.dto.domain.AllEmployeesDomain;
import com.nexus.java.api.domain.dto.domain.LoginAdministratorDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdministratorMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public AdministratorToDomain administratorToDomain(AdministratorLoginDto administratorLoginDto){
        return MODEL_MAPPER.map(administratorLoginDto, AdministratorToDomain.class);
    }

    public AdministratorResponse administratorToResponse(LoginAdministratorDomain loginAdministratorDomain){
        return MODEL_MAPPER.map(loginAdministratorDomain, AdministratorResponse.class);
    }

    public List<AllEmployeesResponse> allEmployeesToResponse(List<AllEmployeesDomain> allEmployeesDomain) {
        return allEmployeesDomain.stream()
                .map(employee -> MODEL_MAPPER.map(employee, AllEmployeesResponse.class))
                .collect(Collectors.toList());
    }

}
