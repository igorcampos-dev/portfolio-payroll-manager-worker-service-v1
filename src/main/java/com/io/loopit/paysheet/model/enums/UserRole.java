package com.io.loopit.paysheet.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN");

    private final String role;
}