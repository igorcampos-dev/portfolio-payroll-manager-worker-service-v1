package controller.admin;

import com.nexus.java.api.application.controller.administrator.PaycheckAdministratorController;
import com.nexus.java.api.application.dto.response.AllEmployeesResponse;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import lombok.SneakyThrows;
import mock.Classes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaycheckAdminControllerTest {

    @Mock
    PaycheckEmployeeService paycheckEmployeeService;

    @Test
    @SneakyThrows
    void sendPaycheckTest(){
        PaycheckAdministratorController paycheckAdminController = new PaycheckAdministratorController(paycheckEmployeeService);

        MultipartFile multipartFile = new MockMultipartFile("filename", new byte[0]);
        String userId = "userId";
        String paycheckDate = "2024-03-22";

        ResponseEntity<?> result = (ResponseEntity<?>) Classes.callMethodByReflection(
         PaycheckAdministratorController.class,
         "sendPaycheck",
         new Class<?>[]{MultipartFile.class, String.class, String.class},
         new Object[]{multipartFile, userId, paycheckDate},
         paycheckAdminController
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(paycheckEmployeeService, times(1)).putFile(any(), anyString(), anyString());

    }

    @Test
    @SneakyThrows
    void allEmployeesTest(){
        PaycheckAdministratorController paycheckAdminController = new PaycheckAdministratorController(paycheckEmployeeService);

        ResponseEntity<List<AllEmployeesResponse>> result = (ResponseEntity<List<AllEmployeesResponse>>) Classes.callMethodByReflection(
                PaycheckAdministratorController.class,
                "allEmployees",
                new Class<?>[]{},
                new Object[]{},
                paycheckAdminController
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(paycheckEmployeeService, times(1)).findAllUsersWithBasicInfo();
    }

    @Test
    @SneakyThrows
    void updatePaycheckOfUserTest(){
        PaycheckAdministratorController paycheckAdminController = new PaycheckAdministratorController(paycheckEmployeeService);

        MultipartFile multipartFile = new MockMultipartFile("filename", new byte[0]);
        String userId = "userId";
        String paycheckDate = "2024-03-22";


        ResponseEntity<?> result = (ResponseEntity<?>) Classes.callMethodByReflection(
                PaycheckAdministratorController.class,
                "updatePaycheckOfUser",
                new Class<?>[]{MultipartFile.class, String.class, String.class},
                new Object[]{multipartFile, userId, paycheckDate},
                paycheckAdminController
        );


        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(paycheckEmployeeService, times(1)).updateFile(any(), anyString(), anyString());
    }

    @Test
    @SneakyThrows
    void deletePaycheckOfUserTest(){
        PaycheckAdministratorController paycheckAdminController = new PaycheckAdministratorController(paycheckEmployeeService);

        String userId = "userId";
        String paycheckDate = "2024-03-22";

        ResponseEntity<?> result = (ResponseEntity<?>) Classes.callMethodByReflection(
                PaycheckAdministratorController.class,
                "deletePaycheckOfUser",
                new Class<?>[]{String.class, String.class},
                new Object[]{userId, paycheckDate},
                paycheckAdminController
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(paycheckEmployeeService, times(1)).deletePaycheckById(anyString(), anyString());

    }
}
