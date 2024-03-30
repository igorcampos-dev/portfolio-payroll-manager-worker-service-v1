package com.nexus.contracheque.mocks;

import com.nexus.contracheque.model.dto.RegisterDTO;

public class RegisterDTOMock {

    public static RegisterDTO get(){
        return RegisterDTO.builder()
                .cpf("678.793.790-37")
                .password("password")
                .build();
    }
}
