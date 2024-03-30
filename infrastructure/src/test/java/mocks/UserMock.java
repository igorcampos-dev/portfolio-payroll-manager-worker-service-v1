package mocks;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserMock {

    public static UserDetails get(){
        return User.builder()
                .username("Teste")
                .password("password")
                .build();
    }
}
