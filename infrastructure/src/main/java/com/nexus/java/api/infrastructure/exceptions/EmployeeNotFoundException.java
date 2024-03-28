package com.nexus.java.api.infrastructure.exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(String s){
        super(s);
    }
}
