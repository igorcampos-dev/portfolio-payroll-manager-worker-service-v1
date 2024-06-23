package com.io.loopit.paysheet.util;

import lombok.SneakyThrows;

public class ValidatePasswordUtils {

    @SneakyThrows(Exception.class)
    public static void validPass(String password){
        if (password.length() < 10 || password.length() > 25){
            throw new Exception("A senha deve ter entre 10 e 25 caracteres");
        }
    }

}
