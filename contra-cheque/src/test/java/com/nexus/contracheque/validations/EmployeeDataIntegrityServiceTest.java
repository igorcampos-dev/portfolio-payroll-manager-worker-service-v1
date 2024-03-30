package com.nexus.contracheque.validations;

import com.nexus.contracheque.exception.EmptyEmployeeListException;
import com.nexus.contracheque.model.dto.AllEmployeesDTO;
import com.nexus.contracheque.validation.impl.EmployeeDataIntegrityServiceImpl;
import com.nexus.contracheque.database.mysql.repository.EmployeeRepository;
import com.nexus.contracheque.exception.EmployeeAlreadyException;
import com.nexus.contracheque.exception.EmployeeNotFoundException;
import com.nexus.contracheque.mocks.EmployeeMock;
import com.nexus.contracheque.model.entity.Employee;
import com.nexus.contracheque.model.entity.enums.Profession;
import com.nexus.contracheque.model.entity.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeDataIntegrityServiceTest {

    @InjectMocks
    EmployeeDataIntegrityServiceImpl employeeOperations;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void TestFindByLoginAuthUser(){

        when(employeeRepository.findByCpf(anyString())).thenReturn(Optional.of(EmployeeMock.get2()));
        UserDetails user = employeeOperations.findByLoginAuth("678.793.790-37");

        assertEquals("678.793.790-37", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));

        verify(employeeRepository, times(1)).findByCpf(anyString());

    }

    @Test
    void TestFindByLoginAuthAdmin(){

        when(employeeRepository.findByCpf(anyString())).thenReturn(Optional.of(EmployeeMock.get3()));
        UserDetails user = employeeOperations.findByLoginAuth("678.793.790-37");

        assertEquals("678.793.790-37", user.getUsername());
        assertEquals("password", user.getPassword());
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));

        verify(employeeRepository, times(1)).findByCpf(anyString());

    }

    @Test
    void TestThrowFindByLoginAuthUser() {
        when(employeeRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeOperations.findByLoginAuth("678.793.790-37"));
    }

    @Test
    void TestFindByCpf() {

        when(employeeRepository.findByCpf(anyString())).thenReturn(Optional.of(EmployeeMock.get()));

        Employee employee = employeeOperations.findByCpf("678.793.790-37");

        assertEquals("Joao", employee.getName());
        assertEquals(123, employee.getCode());
        assertEquals(UserRole.USER.getRole(), employee.getRole().getRole());
        assertEquals("d5c40e61-e91f-45a1-97b7-3925a9f28b78", employee.getId());
        assertEquals(Profession.ATENDENTE_DE_BALCAO.toString(), employee.getProfession().toString());

        verify(employeeRepository, times(1)).findByCpf(anyString());
    }

    @Test
    void TestThrowFindByCpf() {
        when(employeeRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeOperations.findByCpf("678.793.790-37"));
    }

    @Test
    void TestSave(){
        employeeOperations.save(EmployeeMock.get());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void UserExistsTrue(){
        when(employeeRepository.existsByCpf(anyString())).thenReturn(true);
        assertThrows(EmployeeAlreadyException.class, () -> employeeOperations.userExistsByCpf("678.793.790-37"));
        verify(employeeRepository).existsByCpf("678.793.790-37");
    }

    @Test
    void UserExistsFalse(){
        when(employeeRepository.existsByCpf(anyString())).thenReturn(false);
        assertDoesNotThrow(() -> employeeOperations.userExistsByCpf("678.793.790-37"));
        verify(employeeRepository).existsByCpf("678.793.790-37");
    }

    @Test
    void testFindAllUsersWithBasicInfoEmptyList() {
        when(employeeRepository.findAll()).thenReturn(List.of());
        assertThrows(EmptyEmployeeListException.class, () -> employeeOperations.findAllUsersWithBasicInfo());
    }

    @Test
    void testFindAllUsersWithBasicInfoNonEmptyList() {

        when(employeeRepository.findAll()).thenReturn(List.of(
                EmployeeMock.get()
        ));

        List<AllEmployeesDTO> result = employeeOperations.findAllUsersWithBasicInfo();

        assertNotNull(result);
        assertEquals(1, result.size());

        AllEmployeesDTO firstDTO = result.get(0);
        assertEquals("d5c40e61-e91f-45a1-97b7-3925a9f28b78", firstDTO.id());
        assertEquals("Joao", firstDTO.name());
        assertEquals(Profession.ATENDENTE_DE_BALCAO.toString(), String.valueOf(firstDTO.profession()));
    }

    @Test
    void testFindById(){
        when(employeeRepository.findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78")).thenReturn(Optional.of(EmployeeMock.get()));

        Employee employee = employeeOperations.findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78");

        Employee employeeMock = EmployeeMock.get();

        assertEquals(employeeMock.getName(), employee.getName());
        assertEquals(employeeMock.getCode(), employee.getCode());
        assertEquals(employeeMock.getRole().getRole(), employee.getRole().getRole());
        assertEquals(employeeMock.getId(), employee.getId());
        assertEquals(employeeMock.getProfession(), employee.getProfession());
    }

    @Test
    void testFindByIdThrow(){
        when(employeeRepository.findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78")).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeOperations.findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78"));
    }

}
