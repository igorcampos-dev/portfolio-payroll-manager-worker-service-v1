package service;

import com.nexus.java.api.domain.exception.EmployeeNotAdminException;
import com.nexus.java.api.domain.mapper.application.LoginAdministrator;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.model.LoginAdministratorModel;
import com.nexus.java.api.domain.persistence.AdministratorPersistence;
import com.nexus.java.api.domain.service.AdministratorService;
import com.nexus.java.api.domain.service.impl.AdministratorServiceImpl;
import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import com.nexus.security.service.jwt.JwtService;
import com.nexus.utils.Objects;
import mocks.EmployeeMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    AdministratorServiceImpl administratorService;

    @Mock
    AdministratorPersistence administratorPersistence;

    @Mock
    JwtService jwtService;

    @Test
    void login_Successful() {
        LoginAdministratorModel model = new LoginAdministratorModel("67879379037", "password");
        when(administratorPersistence.findByCpf("67879379037")).thenReturn(EmployeeMock.get5());
        when(jwtService.encode(any(Employee.class))).thenReturn("encodedToken");
        LoginAdministrator adminDTO = administratorService.login(model);
        assertEquals("Joao", adminDTO.getName());
        assertEquals("encodedToken", adminDTO.getToken());
    }

    @Test
    void login_NonAdminUser_ThrowsException() {
        LoginAdministratorModel model = new LoginAdministratorModel("67879379037", "password");
        doThrow(EmployeeNotAdminException.class).when(administratorPersistence).findByCpf(anyString());
        assertThrows(EmployeeNotAdminException.class, () -> administratorService.login(model));
    }
}


