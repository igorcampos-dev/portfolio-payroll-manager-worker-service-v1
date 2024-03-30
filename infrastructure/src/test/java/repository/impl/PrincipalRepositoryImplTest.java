package repository.impl;

import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.infrastructure.connect.ConnectionFactory;
import com.nexus.java.api.infrastructure.exceptions.EmployeeNotFoundException;
import com.nexus.java.api.infrastructure.exceptions.SQLNexusException;
import com.nexus.java.api.infrastructure.repository.impl.PrincipalRepositoryImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrincipalRepositoryImplTest {
    @InjectMocks
    private PrincipalRepositoryImpl principalRepository;

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Test
    @SneakyThrows(SQLException.class)
    public void testFindDescriptionByCpf(){
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("code")).thenReturn(123);
        when(resultSet.getString("profession")).thenReturn("CAIXA");
        when(resultSet.getString("name")).thenReturn("Igor de campos");
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        EmployeeDbPrincipal employee = principalRepository.findDescriptionByCpf("12345678900");

        assertNotNull(employee);
        verify(connectionFactory, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        assertEquals("CAIXA", employee.getProfession().name());
        assertEquals("Igor de campos", employee.getName());
        assertEquals(123, employee.getCode());
    }

    @Test
    @SneakyThrows(SQLException.class)
    void testFindDescriptionByCpfThrow(){
        doThrow(new SQLException()).when(connectionFactory).getConnection();
        assertThrows(SQLNexusException.class, () -> principalRepository.findDescriptionByCpf("05605662040"));
        verify(connection, never()).prepareStatement(anyString());
        verify(connectionFactory, times(1)).clean();
    }

    @Test
    @SneakyThrows(SQLException.class)
    void testFindDescriptionByCpfThrow2() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doThrow(EmployeeNotFoundException.class).when(preparedStatement).executeQuery();
        assertThrows(EmployeeNotFoundException.class, () -> principalRepository.findDescriptionByCpf("05605662040"));
    }
}
