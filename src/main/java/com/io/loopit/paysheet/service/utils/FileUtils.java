package com.io.loopit.paysheet.service.utils;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import java.util.Base64;
import java.util.Objects;

public class FileUtils {

    public static String replaceUrlEncodedSpaces(String object) {
        return object.replace("%20", " ");
    }

     public static String toBase64(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void validateContentTypeIsApplicationPdf(String contentType) {
        boolean isInvalidContentType = Objects.equals(contentType, "application/pdf");
        if (!isInvalidContentType) {
            throw new InvalidDataAccessApiUsageException("Tipo de arquivo inválido, use coloque apenas pdf");
        }
    }


}