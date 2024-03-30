package mocks;

import com.nexus.java.api.domain.model.LoginEmployeeModel;

public class LoginDTOMock {

    public static LoginEmployeeModel get(){
        return LoginEmployeeModel.builder()
                .cpf("678.793.790-37")
                .password("password")
                .build();
    }
}
