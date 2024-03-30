package controller.employee;


import com.nexus.java.api.application.controller.employee.AuthenticationEmployeeController;
import com.nexus.java.api.application.dto.request.LoginEmployeeDto;
import com.nexus.java.api.application.dto.request.RegisterEmployeeDto;
import com.nexus.java.api.domain.service.EmployeeService;
import lombok.SneakyThrows;
import mock.Classes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthenticationEmployeeControllerTest {

    @Mock
    EmployeeService employeeService;

    @Test
    @SneakyThrows(Exception.class)
    void authenticateEmployeeLoginTest(){
        AuthenticationEmployeeController authEmployeeController = new AuthenticationEmployeeController(employeeService);
        LoginEmployeeDto loginDTO = LoginEmployeeDto.builder().cpf("05605662040").password("3570951").build();

        ResponseEntity<?> result = (ResponseEntity<?>) Classes.callMethodByReflection(
                AuthenticationEmployeeController.class,
                "authenticateEmployeeLogin",
                new Class<?>[]{LoginEmployeeDto.class},
                new Object[]{loginDTO},
                authEmployeeController
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(employeeService, times(1)).login(any());
    }

    @Test
    @SneakyThrows(Exception.class)
    void registerEmployeeAccount(){
        AuthenticationEmployeeController authEmployeeController = new AuthenticationEmployeeController(employeeService);
        RegisterEmployeeDto registerDTO = RegisterEmployeeDto.builder().cpf("05605662040").password("3570951").build();

        ResponseEntity<?> result = (ResponseEntity<?>) Classes.callMethodByReflection(
                AuthenticationEmployeeController.class,
                "registerEmployeeAccount",
                new Class<?>[]{RegisterEmployeeDto.class},
                new Object[]{registerDTO},
                authEmployeeController
        );

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(employeeService, times(1)).register(any());
    }
}
