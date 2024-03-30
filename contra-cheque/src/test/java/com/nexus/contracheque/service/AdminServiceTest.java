package com.nexus.contracheque.service;

import com.nexus.contracheque.exception.EmployeeNotAdminException;
import com.nexus.contracheque.mocks.EmployeeMock;
import com.nexus.contracheque.model.dto.AdminDTO;
import com.nexus.contracheque.model.dto.LoginDTO;
import com.nexus.contracheque.model.entity.Employee;
import com.nexus.contracheque.service.impl.AdminServiceImpl;
import com.nexus.contracheque.validation.EmployeeDataIntegrityService;
import com.nexus.security.service.jwt.JwtService;
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
    AdminServiceImpl adminService;

    @Mock
    EmployeeDataIntegrityService employeeDataIntegrityService;

    @Mock
    JwtService jwtService;

    @Test
    void login_Successful() {
        LoginDTO loginDTO = new LoginDTO("67879379037", "password");
        when(employeeDataIntegrityService.findByCpf("67879379037")).thenReturn(EmployeeMock.get3());
        when(jwtService.encode(any(Employee.class))).thenReturn("encodedToken");

        AdminDTO adminDTO = adminService.login(loginDTO);

        assertEquals("Igor", adminDTO.name());
        assertEquals("encodedToken", adminDTO.token());
    }

    @Test
    void login_NonAdminUser_ThrowsException() {
        LoginDTO loginDTO = new LoginDTO("67879379037", "password");
        when(employeeDataIntegrityService.findByCpf("67879379037")).thenReturn(EmployeeMock.get());

        assertThrows(EmployeeNotAdminException.class, () -> adminService.login(loginDTO));
    }
}
