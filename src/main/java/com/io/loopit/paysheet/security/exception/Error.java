package com.io.loopit.paysheet.security.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    Instant timestamp;
    Integer status;
    String message;
    String path;
}