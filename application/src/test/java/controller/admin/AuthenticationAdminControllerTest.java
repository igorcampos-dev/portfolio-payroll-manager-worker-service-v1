package controller.admin;

import com.nexus.java.api.application.controller.administrator.AuthenticateAdministratorController;
import com.nexus.java.api.application.dto.request.AdministratorLoginDto;
import com.nexus.java.api.application.dto.response.AdministratorResponse;
import com.nexus.java.api.domain.service.AdministratorService;
import lombok.SneakyThrows;
import mock.Classes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthenticationAdminControllerTest {

    @Mock
    AdministratorService adminService;

    @Test
    @SneakyThrows
    void loginTest() {
        AdministratorLoginDto loginDTO = AdministratorLoginDto.builder().cpf("05605662040").password("3570951").build();
        AuthenticateAdministratorController authAdminController = new AuthenticateAdministratorController(adminService);

        ResponseEntity<AdministratorResponse> result = (ResponseEntity<AdministratorResponse>) Classes.callMethodByReflection(
                AuthenticateAdministratorController.class,
                "login",
                new Class<?>[]{AdministratorLoginDto.class},
                new Object[]{loginDTO},
                authAdminController
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
