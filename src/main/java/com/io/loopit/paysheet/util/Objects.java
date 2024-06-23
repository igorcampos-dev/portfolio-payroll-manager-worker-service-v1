package com.io.loopit.paysheet.util;

public class Objects {

    public static void throwIfTrue(boolean condition, RuntimeException exception) {
        if (condition) {
            throw exception;
        }
    }

}
