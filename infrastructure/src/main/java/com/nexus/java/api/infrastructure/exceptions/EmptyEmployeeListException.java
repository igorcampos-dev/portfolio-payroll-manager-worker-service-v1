package com.nexus.java.api.infrastructure.exceptions;

public class EmptyEmployeeListException extends RuntimeException {
    public EmptyEmployeeListException(String s){
        super(s);
    }
}
