package com.nexus.java.api.application.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGeneric {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows(JsonProcessingException.class)
    public static ResponseEntity<?> response(HttpStatus httpStatus, Object responseParam){
        return ResponseEntity.status(httpStatus).body(OBJECT_MAPPER.writeValueAsString(responseParam));
    }
}
