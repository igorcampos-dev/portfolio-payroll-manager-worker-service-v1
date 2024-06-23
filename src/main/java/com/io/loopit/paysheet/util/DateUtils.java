package com.io.loopit.paysheet.util;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

    private static final Pattern PATTERN = Pattern.compile("^(0[1-9]|1[0-2])-(19|20)\\d{2}$");

    public static void isValidMonthYearFormat(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new InvalidDataAccessApiUsageException("Data inválida. O formato correto é MM-AAAA.");
        }
    }

}