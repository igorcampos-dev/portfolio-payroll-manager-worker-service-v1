package com.io.loopit.paysheet.controller.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class ResponseGeneric {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows(JsonProcessingException.class)
    public static ResponseEntity<?> response(HttpStatus httpStatus, Object responseParam) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", responseParam);
        String responseBody = OBJECT_MAPPER.writeValueAsString(responseMap);
        return ResponseEntity.status(httpStatus).body(responseBody);
    }
}
