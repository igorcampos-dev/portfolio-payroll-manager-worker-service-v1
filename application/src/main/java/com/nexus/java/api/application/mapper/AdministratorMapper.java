package com.nexus.java.api.application.mapper;

import com.nexus.java.api.application.dto.request.AdministratorLoginDto;
import com.nexus.java.api.application.dto.response.AdministratorResponse;
import com.nexus.java.api.domain.dto.AdministratorToDomain;
import com.nexus.java.api.domain.dto.domain.LoginDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdministratorMapper {

    public AdministratorToDomain toDomain(AdministratorLoginDto administratorLoginDto){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(administratorLoginDto, AdministratorToDomain.class);
    }

    public AdministratorResponse toResponse(LoginDomain loginDomain){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(loginDomain, AdministratorResponse.class);
    }

}
