package com.io.loopit.paysheet.repository.impl;

import com.io.loopit.paysheet.config.connect.ConnectionFactory;
import com.io.loopit.paysheet.model.EmployeePrincipalEntity;
import com.io.loopit.paysheet.model.enums.Profession;
import com.io.loopit.paysheet.repository.PrincipalRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    @SneakyThrows(SQLException.class)
    public EmployeePrincipalEntity findDescriptionByCpf(String cpf) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT code, profession, name FROM employees WHERE cpf = ?")) {
             statement.setString(1, cpf);
             return this.getResult(statement);
        } catch (SQLException e) {
            connectionFactory.clean();
            throw new SQLException("houve um erro interno ao executar o sql.");
        }
    }

    private EmployeePrincipalEntity getEmployee(ResultSet resultSet) throws SQLException {
        return EmployeePrincipalEntity.builder()
                .code(resultSet.getInt("code"))
                .profession(Profession.valueOf(resultSet.getString("profession")))
                .name(resultSet.getString("name"))
                .build();
    }

    private EmployeePrincipalEntity getResult(PreparedStatement statement) throws SQLException {

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return this.getEmployee(resultSet);
            }
            throw new NullPointerException("Usuário não existe.");
            }
    }
}