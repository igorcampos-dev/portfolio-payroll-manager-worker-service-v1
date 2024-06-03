package com.io.loopit.paysheet.controller.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdministratorResponse {

    String name;
    String token;

}
