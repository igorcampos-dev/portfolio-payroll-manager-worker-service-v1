package com.io.loopit.paysheet.util;

public interface ValidationsUtils {
    void validateMonthYearFormat(String input);
    void validateContentType(String contentType);
    void validatePassword(String password);
    void validateUUID(String uuidString);
}
