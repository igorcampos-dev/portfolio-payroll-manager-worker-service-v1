package com.nexus.java.api.domain.exception;

public class EmployeeNotAdminException extends RuntimeException {
    public EmployeeNotAdminException(String s){
        super(s);
    }
}
