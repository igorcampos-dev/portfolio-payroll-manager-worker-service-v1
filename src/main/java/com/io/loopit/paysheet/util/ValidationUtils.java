package com.io.loopit.paysheet.util;

import lombok.SneakyThrows;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static void validatePostAction(String userId, String paycheckDate, String contentType){
        validateUUID(userId);
        validateMonthYearFormat(paycheckDate);
        validateContentType(contentType);
    }

    public static void validatePutAction(String userId, String paycheckDate, String contentType){
        validateUUID(userId);
        validateMonthYearFormat(paycheckDate);
        validateContentType(contentType);
    }

    public static void validateDeleteAction(String userId, String paycheckDate){
        validateUUID(userId);
        validateMonthYearFormat(paycheckDate);
    }

    public static void validateGetAction(String userId, String paycheckDate){
        validateUUID(userId);
        validateMonthYearFormat(paycheckDate);
    }

    public static void validateUuid(String uuid){
        validateUUID(uuid);
    }

    public static void validatePassword(String password){
        validatePass(password);
    }

    private static void validateMonthYearFormat(String input) {
        Matcher matcher = Pattern.compile("^(0[1-9]|1[0-2])-(19|20)\\d{2}$")
                .matcher(input);
        if (!matcher.matches()) {
            throw new InvalidDataAccessApiUsageException("Data inválida. O formato correto é MM-AAAA.");
        }
    }

    private static void validateContentType(String contentType) {
        if (!Objects.equals(contentType, "application/pdf")) {
            throw new InvalidDataAccessApiUsageException("Tipo de arquivo inválido, use coloque apenas pdf");
        }
    }

    @SneakyThrows(Exception.class)
    private static void validatePass(String password) {
        if (password.length() < 10 || password.length() > 25){
            throw new Exception("A senha deve ter entre 10 e 25 caracteres");
        }
    }

    private static void validateUUID(String uuidString) {
        try {
            UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Um campo fornecido não é um UUID válido.", e);
        }
    }

}
