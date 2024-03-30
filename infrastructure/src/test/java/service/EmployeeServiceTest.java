package service;

import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.model.RegisterEmployeeModel;
import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import com.nexus.java.api.domain.service.impl.EmployeeServiceImpl;
import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import com.nexus.java.api.infrastructure.exceptions.EmployeeAlreadyException;
import com.nexus.java.api.infrastructure.exceptions.EmployeeNotFoundException;
import com.nexus.java.api.infrastructure.exceptions.SQLNexusException;
import com.nexus.java.api.infrastructure.repository.PrincipalRepository;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import mocks.*;
import org.springframework.security.core.Authentication;
import com.nexus.security.service.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeServiceImpl userService;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    PrincipalRepository otherDbOperations;

    @Mock
    Authentication authentication;

    @Mock
    PaycheckEmployeeService paycheckEmployeeService;

    @Mock
    EmployeePersistence employeePersistence;


    @Test
    void Login(){
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(UserMock.get(), new ArrayList<>());
        when(jwtService.encode(UserMock.get())).thenReturn(TokenMock.get());
        when(employeePersistence.findByCpf(LoginDTOMock.get().getCpf())).thenReturn(EmployeeMock.get4());
        userService.login(LoginDTOMock.get());
        verify(jwtService, times(1)).encode(any(UserDetails.class));
        verify(employeePersistence, times(1)).findByCpf(anyString());

    }

    @Test
    void LoginException(){

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(UserMock.get(), new ArrayList<>());

        doThrow(new RuntimeException()).when(jwtService).encode(UserMock.get());
        assertThrows(RuntimeException.class, () -> userService.login(LoginDTOMock.get()));
        verify(employeePersistence, never()).findByCpf(LoginDTOMock.get().getCpf());

    }

    @Test
    void LoginUserNotFoundException(){
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(UserMock.get(), new ArrayList<>());
        doThrow(EmployeeNotFoundException.class).when(employeePersistence).findByCpf(anyString());
        assertThrows(EmployeeNotFoundException.class, () -> userService.login(LoginDTOMock.get()));
    }

    @Test
    void Register(){

        doNothing().when(employeePersistence).userExistsByCpf(anyString());
        when(employeePersistence.findDescriptionByCpf(anyString())).thenReturn(EmployeeDbPrincipalMock.get());
        userService.register(RegisterEmployeeModelMock.get());
        verify(employeePersistence, times(1)).userExistsByCpf(anyString());
        verify(employeePersistence, times(1)).findDescriptionByCpf(anyString());
        verify(employeePersistence, times(1)).save(any(EmployeeDbPrincipal.class), any(RegisterEmployeeModel.class));
        verify(paycheckEmployeeService, times(1)).createFolder(anyString());
    }

    @Test
    void testRegisterUserAlreadyException() {
        doThrow(EmployeeAlreadyException.class).when(employeePersistence).userExistsByCpf(anyString());
        assertThrows(EmployeeAlreadyException.class, () -> userService.register(RegisterEmployeeModelMock.get()));
        verify(otherDbOperations, never()).findDescriptionByCpf(anyString());
        verify(employeePersistence, never()).save(any(EmployeeDbPrincipal.class), any(RegisterEmployeeModel.class));
        verify(paycheckEmployeeService, never()).createFolder(anyString());

    }

    @Test
    void testRegisterSQLNexusException(){
        doThrow(SQLNexusException.class).when(employeePersistence).findDescriptionByCpf(anyString());
        assertThrows(SQLNexusException.class, () -> userService.register(RegisterEmployeeModelMock.get()));
        verify(employeePersistence, never()).save(any(EmployeeDbPrincipal.class), any(RegisterEmployeeModel.class));
        verify(paycheckEmployeeService, never()).createFolder(anyString());

    }

    String getCpf(){
        return "67879379037";
    }


}

