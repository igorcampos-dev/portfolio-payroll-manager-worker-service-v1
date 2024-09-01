package com.io.loopit.paysheet.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String message;

    public static MessageResponse build(String message) {
        return MessageResponse.builder()
                .message(message)
                .build();
    }
}
