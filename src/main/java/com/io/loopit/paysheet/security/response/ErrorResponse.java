package com.io.loopit.paysheet.security.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String path;

    public ErrorResponse(String message, HttpServletRequest request) {
        timestamp = LocalDateTime.now();
        this.message = message;
        this.path = request.getRequestURI();
    }

    @SneakyThrows(IOException.class)
    public static void getError(HttpServletResponse response, Exception e, HttpServletRequest request){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(
                response.getWriter(),
                new ErrorResponse(e.getMessage(), request)
        );
    }

}
