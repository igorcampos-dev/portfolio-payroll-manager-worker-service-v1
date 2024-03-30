package controller.employee;

import com.nexus.java.api.application.controller.employee.PaycheckEmployeeController;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import lombok.SneakyThrows;
import mock.Classes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaycheckEmployeeControllerTest {

    @Mock
    PaycheckEmployeeService paycheckEmployeeService;

    @Test
    @SneakyThrows(Exception.class)
    void getPaychecksByUserId(){
        PaycheckEmployeeController paycheckEmployeeController = new PaycheckEmployeeController(paycheckEmployeeService);
        String userId = "userId";

        ResponseEntity<?> result = (ResponseEntity<?>) Classes.callMethodByReflection(
                PaycheckEmployeeController.class,
                "getPaychecksByUserId",
                new Class<?>[]{String.class},
                new Object[]{userId},
                paycheckEmployeeController
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(paycheckEmployeeService, times(1)).getPaychecksByUserId(anyString());


    }

    @Test
    @SneakyThrows(Exception.class)
    void getPaycheckByUserIdAndPaycheckDate(){
        PaycheckEmployeeController paycheckEmployeeController = new PaycheckEmployeeController(paycheckEmployeeService);
        String userId = "userId";
        String paycheckDate = "2024-03-22";

        ResponseEntity<?> result = (ResponseEntity<?>) Classes.callMethodByReflection(
                PaycheckEmployeeController.class,
                "getPaycheckByUserIdAndPaycheckDate",
                new Class<?>[]{String.class, String.class},
                new Object[]{userId, paycheckDate},
                paycheckEmployeeController
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(paycheckEmployeeService, times(1)).getContentFile(anyString(), anyString());

    }
}
