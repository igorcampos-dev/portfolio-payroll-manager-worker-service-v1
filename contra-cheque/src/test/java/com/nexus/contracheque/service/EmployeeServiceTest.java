package com.nexus.contracheque.service;

import com.nexus.contracheque.exception.SQLNexusException;
import com.nexus.contracheque.exception.EmployeeAlreadyException;
import com.nexus.contracheque.exception.EmployeeNotFoundException;
import com.nexus.contracheque.mocks.*;
import org.springframework.security.core.Authentication;
import com.nexus.contracheque.validation.EmployeeDataIntegrityService;
import com.nexus.contracheque.database.mysql.repository.PrincipalRepository;
import com.nexus.contracheque.model.entity.Employee;
import com.nexus.contracheque.service.impl.EmployeeServiceImpl;
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
    EmployeeDataIntegrityService employeeOperations;

    @Mock
    PrincipalRepository otherDbOperations;

    @Mock
    Authentication authentication;

    @Mock
    PaycheckEmployeeService  paycheckEmployeeService;


    @Test
    void Login(){

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(UserMock.get(), new ArrayList<>());
        when(jwtService.encode(UserMock.get())).thenReturn(TokenMock.get());
        when(employeeOperations.findByCpf(LoginDTOMock.get().getCpf())).thenReturn(EmployeeMock.get());

        userService.login(LoginDTOMock.get());

        verify(jwtService, times(1)).encode(any(UserDetails.class));
        verify(employeeOperations, times(1)).findByCpf(anyString());

    }

    @Test
    void LoginException(){

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(UserMock.get(), new ArrayList<>());

        doThrow(new RuntimeException()).when(jwtService).encode(UserMock.get());
        assertThrows(RuntimeException.class, () -> userService.login(LoginDTOMock.get()));
        verify(employeeOperations, never()).findByCpf(LoginDTOMock.get().getCpf());

    }

    @Test
    void LoginUserNotFoundException(){

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(UserMock.get(), new ArrayList<>());

        doThrow(new EmployeeNotFoundException("Usuário não existe."))
                .when(employeeOperations).findByCpf(this.getCpf());

        assertThrows(EmployeeNotFoundException.class, () -> userService.login(LoginDTOMock.get()));
    }

    @Test
    void Register(){

        doNothing().when(employeeOperations).userExistsByCpf(anyString());
        when(otherDbOperations.findDescriptionByCpf(anyString())).thenReturn(EmployeeBaseMasterMock.get());
        doNothing().when(employeeOperations).save(any(Employee.class));

        userService.register(RegisterDTOMock.get());

        verify(employeeOperations, times(1)).userExistsByCpf(RegisterDTOMock.get().getCpf());
        verify(otherDbOperations, times(1)).findDescriptionByCpf(RegisterDTOMock.get().getCpf());
        verify(employeeOperations, times(1)).save(any(Employee.class));
        verify(paycheckEmployeeService, times(1)).createFolder(anyString());
    }

    @Test
    void testRegisterUserAlreadyException() {
        doThrow(new EmployeeAlreadyException("Usuário já existe."))
                .when(employeeOperations).userExistsByCpf(this.getCpf());

        assertThrows(EmployeeAlreadyException.class, () -> userService.register(RegisterDTOMock.get()));

        verify(otherDbOperations, never()).findDescriptionByCpf(anyString());
        verify(employeeOperations, never()).save(any(Employee.class));
        verify(paycheckEmployeeService, never()).createFolder(anyString());

    }

    @Test
    void testRegisterSQLNexusException(){

        doThrow(new SQLNexusException("houve um erro interno ao executar o sql."))
                .when(otherDbOperations).findDescriptionByCpf(this.getCpf());

        assertThrows(SQLNexusException.class, () -> userService.register(RegisterDTOMock.get()));
        verify(employeeOperations, never()).save(any(Employee.class));
        verify(paycheckEmployeeService, never()).createFolder(anyString());

    }

    String getCpf(){
        return "67879379037";
    }


}
