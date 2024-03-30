package mocks;

import com.nexus.java.api.domain.model.RegisterEmployeeModel;

public class RegisterEmployeeModelMock {

    public static RegisterEmployeeModel get(){
        return RegisterEmployeeModel.builder()
                .cpf("678.793.790-37")
                .password("password")
                .build();
    }
}
