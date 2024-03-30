package com.nexus.contracheque.service;

import com.nexus.aws.cloud.S3;
import com.nexus.aws.model.S3File;
import com.nexus.contracheque.exception.EmployeeNotFoundException;
import com.nexus.contracheque.exception.EmptyEmployeeListException;
import com.nexus.contracheque.exception.InvalidContentTypeException;
import com.nexus.contracheque.exception.InvalidPaycheckDateException;
import com.nexus.contracheque.mocks.EmployeeMock;
import com.nexus.contracheque.mocks.S3FileMock;
import com.nexus.contracheque.model.dto.AllEmployeesDTO;
import com.nexus.contracheque.service.impl.PaycheckEmployeeServiceImpl;
import com.nexus.contracheque.validation.EmployeeDataIntegrityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaycheckEmployeeServiceTest {

    @InjectMocks
    PaycheckEmployeeServiceImpl paycheckEmployeeService;

    @Mock
    EmployeeDataIntegrityService employeeDataIntegrityService;

    @Mock
    S3 s3;


    @Test
    void getPaychecksByUserIdTest() {

        when(employeeDataIntegrityService.findById(anyString())).thenReturn(EmployeeMock.get());
        when(s3.listObjectsInFolder("joao")).thenReturn(S3FileMock.ListS3FileMock());
        List<S3File> paychecks = paycheckEmployeeService.getPaychecksByUserId("a53cc484-8e68-40a4-8701-47cb38ad0bd6");

        assertNotNull(paychecks);
        assertFalse(paychecks.isEmpty());
        verify(employeeDataIntegrityService, times(1)).findById("a53cc484-8e68-40a4-8701-47cb38ad0bd6");
        verify(s3, times(1)).listObjectsInFolder("joao");
    }

    @Test
    void  getPaychecksByUserIdUserNotFoundExceptionTest(){

        doThrow(new EmployeeNotFoundException("Usuário não existe."))
                .when(employeeDataIntegrityService).findById("a53cc484-8e68-40a4-8701-47cb38ad0bd6");

        assertThrows(EmployeeNotFoundException.class, () -> paycheckEmployeeService.getPaychecksByUserId("a53cc484-8e68-40a4-8701-47cb38ad0bd6"));
        verify(s3, never()).listObjectsInFolder(anyString());
    }

    @Test
    void testCreateFolder() {
        String folderName = "Test Folder";
        paycheckEmployeeService.createFolder(folderName);
        verify(s3, times(1)).createFolder("test-folder");
    }

    @Test
    void testDeletePaycheckById_Success() {

        when(employeeDataIntegrityService.findById(EmployeeMock.get().getId())).thenReturn(EmployeeMock.get());

        paycheckEmployeeService.deletePaycheckById("d5c40e61-e91f-45a1-97b7-3925a9f28b78", "01-2023");

        verify(s3, times(1)).deleteFile("joao", "01-2023");
    }

    @Test
    void testDeletePaycheckById_EmployeeNotFound() {
        doThrow(new EmployeeNotFoundException("Usuário não existe."))
                .when(employeeDataIntegrityService).findById(EmployeeMock.get().getId());


        assertThrows(EmployeeNotFoundException.class, () -> paycheckEmployeeService.deletePaycheckById(EmployeeMock.get().getId(), "01-2023"));
        verify(s3, never()).deleteFile(anyString(), anyString());
    }

    @Test
    void testFindAllUsersWithBasicInfo(){
        when(employeeDataIntegrityService.findAllUsersWithBasicInfo()).thenReturn(EmployeeMock.list());
       List<AllEmployeesDTO> allEmployeesDTOS = paycheckEmployeeService.findAllUsersWithBasicInfo();
        assertNotNull(allEmployeesDTOS);
    }

    @Test
    void testFindAllUsersWithBasicInfoThrow(){
        doThrow(new EmptyEmployeeListException("Não existe usuários cadastrados")).when(employeeDataIntegrityService).findAllUsersWithBasicInfo();
        assertThrows(EmptyEmployeeListException.class, () -> paycheckEmployeeService.findAllUsersWithBasicInfo());
    }

    @Test
    void getContentFileTestThrow(){
        assertThrows(InvalidPaycheckDateException.class, () -> paycheckEmployeeService.getContentFile("teste", "teste"));
        verify(employeeDataIntegrityService, never()).findById(anyString());
        verify(s3, never()).getFile(anyString(), anyString());
    }

    @Test
    void getContentFileTest(){
        String folder = EmployeeMock.get().getName().replace(" ", "-").toLowerCase().trim();
        when(employeeDataIntegrityService.findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78")).thenReturn(EmployeeMock.get());
        when(s3.getFile(folder, "12-2023")).thenReturn(new byte[1]);

        paycheckEmployeeService.getContentFile("d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023");

        verify(employeeDataIntegrityService, times(1)).findById(anyString());
        verify(s3, times(1)).getFile(anyString(), anyString());
    }

    @Test
    void getContentFileTestThrow2(){
        doThrow(new EmployeeNotFoundException("Usuário não existe.")).when(employeeDataIntegrityService).findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78");

        assertThrows(EmployeeNotFoundException.class, () -> paycheckEmployeeService.getContentFile("d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023"))  ;
        verify(employeeDataIntegrityService, times(1)).findById(anyString());
        verify(s3, never()).getFile(anyString(), anyString());
    }

    @Test
    void updateFileThrow(){
        MultipartFile multipartFile = new MockMultipartFile("filename", new byte[0]);

        assertThrows(InvalidPaycheckDateException.class, () -> paycheckEmployeeService.updateFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "23-2023"));
        verify(employeeDataIntegrityService, never()).findById(anyString());
        verify(s3, never()).updateObject(any(), anyString(), anyString());
    }

    @Test
    void updateFileThrow2(){
        MultipartFile multipartFile = new MockMultipartFile("filename", new byte[0]);
        assertThrows(InvalidContentTypeException.class, () -> paycheckEmployeeService.updateFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023"));
        verify(employeeDataIntegrityService, never()).findById(anyString());
        verify(s3, never()).updateObject(any(), anyString(), anyString());
    }

    @Test
    void updateFileTrhow3(){
        MultipartFile multipartFile = new MockMultipartFile("filename", "filename", "application/pdf", new byte[0]);

        doThrow(new EmployeeNotFoundException("Usuário não existe.")).when(employeeDataIntegrityService).findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78");

        assertThrows(EmployeeNotFoundException.class, () -> paycheckEmployeeService.updateFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023"))  ;
        verify(employeeDataIntegrityService, times(1)).findById(anyString());
        verify(s3, never()).updateObject(any(), anyString(), anyString());
    }

    @Test
    void updateFile(){
        MultipartFile multipartFile = new MockMultipartFile("filename", "filename", "application/pdf", new byte[0]);

        when(employeeDataIntegrityService.findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78")).thenReturn(EmployeeMock.get());

        paycheckEmployeeService.updateFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023" );

        verify(employeeDataIntegrityService, times(1)).findById(anyString());
        verify(s3, times(1)).updateObject(any(), anyString(), anyString());
    }

    @Test
    void putFileThrow(){
        MultipartFile multipartFile = new MockMultipartFile("filename", "filename", "application/pdf", new byte[0]);

        assertThrows(InvalidPaycheckDateException.class, () -> paycheckEmployeeService.putFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "23-2023"));
        verify(employeeDataIntegrityService, never()).findById(anyString());
        verify(s3, never()).putObject(any(), anyString(), anyString());
    }

    @Test
    void putFileThrow2(){
        MultipartFile multipartFile = new MockMultipartFile("filename", new byte[0]);
        assertThrows(InvalidContentTypeException.class, () -> paycheckEmployeeService.putFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023"));
        verify(employeeDataIntegrityService, never()).findById(anyString());
        verify(s3, never()).putObject(any(), anyString(), anyString());
    }

    @Test
    void putFileTrhow3(){
        MultipartFile multipartFile = new MockMultipartFile("filename", "filename", "application/pdf", new byte[0]);

        doThrow(new EmployeeNotFoundException("Usuário não existe.")).when(employeeDataIntegrityService).findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78");

        assertThrows(EmployeeNotFoundException.class, () -> paycheckEmployeeService.putFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023"))  ;
        verify(employeeDataIntegrityService, times(1)).findById(anyString());
        verify(s3, never()).putObject(any(), anyString(), anyString());
    }

    @Test
    void putFile(){
        MultipartFile multipartFile = new MockMultipartFile("filename", "filename", "application/pdf", new byte[0]);

        when(employeeDataIntegrityService.findById("d5c40e61-e91f-45a1-97b7-3925a9f28b78")).thenReturn(EmployeeMock.get());

        paycheckEmployeeService.putFile(multipartFile, "d5c40e61-e91f-45a1-97b7-3925a9f28b78", "12-2023" );

        verify(employeeDataIntegrityService, times(1)).findById(anyString());
        verify(s3, times(1)).putObject(any(), anyString(), anyString());
    }







}
