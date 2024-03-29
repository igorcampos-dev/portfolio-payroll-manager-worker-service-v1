package com.nexus.java.api.domain.exception;

public class UserNotAdminException extends RuntimeException {
    public UserNotAdminException(String s){
        super(s);
    }
}
