package com.nexus.java.api.application.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T convert(Object input, Class<T> targetType) {
        return OBJECT_MAPPER.convertValue(input, targetType);
    }

    public static <T, U> List<U> convertList(List<T> inputList, Class<U> targetType) {
        List<U> resultList = new ArrayList<>();
        inputList.forEach(input -> {
            U converted = OBJECT_MAPPER.convertValue(input, targetType);
            resultList.add(converted);
        });

        return resultList;
    }
}
