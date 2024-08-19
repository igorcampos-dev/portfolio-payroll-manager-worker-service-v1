package com.io.loopit.paysheet.util;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@SuppressWarnings("unused")
public class ValidationsUtilsImpl implements ValidationsUtils {

    @Override
    public void validateMonthYearFormat(String input) {
        Matcher matcher = Pattern.compile("^(0[1-9]|1[0-2])-(19|20)\\d{2}$")
                .matcher(input);
        if (!matcher.matches()) {
            throw new InvalidDataAccessApiUsageException("Data inválida. O formato correto é MM-AAAA.");
        }
    }

    @Override
    public void validateContentType(String contentType) {
        if (!Objects.equals(contentType, "application/pdf")) {
            throw new InvalidDataAccessApiUsageException("Tipo de arquivo inválido, use coloque apenas pdf");
        }
    }

    @Override
    @SneakyThrows(Exception.class)
    public void validatePassword(String password) {
        if (password.length() < 10 || password.length() > 25){
            throw new Exception("A senha deve ter entre 10 e 25 caracteres");
        }
    }

    @Override
    public void validateUUID(String uuidString) {
        try {
            UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Um campo fornecido não é um UUID válido.", e);
        }
    }

}
