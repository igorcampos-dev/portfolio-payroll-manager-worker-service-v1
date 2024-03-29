package com.nexus.java.api.infrastructure.exceptions;

public class EmployeeAlreadyException extends RuntimeException {
    public EmployeeAlreadyException(String s){
        super(s);
    }
}
