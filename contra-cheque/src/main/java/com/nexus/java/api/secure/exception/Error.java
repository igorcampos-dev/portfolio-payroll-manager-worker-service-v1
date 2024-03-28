package com.nexus.java.api.secure.exception;

import lombok.Builder;
import java.time.Instant;
@Builder
public record Error(Instant timestamp, Integer status, String message, String path) {
}