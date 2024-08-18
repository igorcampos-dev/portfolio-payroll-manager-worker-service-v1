package com.io.loopit.paysheet.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Base64Response {
    private String base64;

    public static Base64Response buildBase64(String response) {
        return Base64Response.builder()
                             .base64(response)
                             .build();
    }
}
