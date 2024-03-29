package com.nexus.java.api.infrastructure.repository.impl;

import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.mapper.infrastructure.enums.Profession;
import com.nexus.java.api.infrastructure.connect.ConnectionFactory;
import com.nexus.java.api.infrastructure.exceptions.EmployeeNotFoundException;
import com.nexus.java.api.infrastructure.exceptions.SQLNexusException;
import com.nexus.java.api.infrastructure.repository.PrincipalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class PrincipalRepositoryImpl implements PrincipalRepository {

    private final ConnectionFactory connectionFactory;


    @Override
    public EmployeeDbPrincipal findDescriptionByCpf(String cpf) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT code, profession, name FROM employees WHERE cpf = ?")) {
             statement.setString(1, cpf);
             return this.getResult(statement);
        } catch (SQLException e) {
            connectionFactory.clean();
            throw SQL_NEXUS_EXCEPTION;
        }
    }

    private EmployeeDbPrincipal getEmployee(ResultSet resultSet) throws SQLException {
        return EmployeeDbPrincipal.builder()
                .code(resultSet.getInt("code"))
                .profession(Profession.valueOf(resultSet.getString("profession")))
                .name(resultSet.getString("name"))
                .build();
    }

    private EmployeeDbPrincipal getResult(PreparedStatement statement) throws SQLException {

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return this.getEmployee(resultSet);
            }
            throw USER_NOT_FOUND_EXCEPTION;
            }
    }

    private static final EmployeeNotFoundException USER_NOT_FOUND_EXCEPTION = new EmployeeNotFoundException("Usuário não existe.");
    private static final SQLNexusException SQL_NEXUS_EXCEPTION = new SQLNexusException("houve um erro interno ao executar o sql.");
}