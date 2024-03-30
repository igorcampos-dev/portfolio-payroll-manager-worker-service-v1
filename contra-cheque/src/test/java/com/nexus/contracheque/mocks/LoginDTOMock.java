package com.nexus.contracheque.mocks;

import com.nexus.contracheque.model.dto.LoginDTO;

public class LoginDTOMock {

    public static LoginDTO get(){
        return LoginDTO.builder()
                .cpf("678.793.790-37")
                .password("password")
                .build();
    }
}
