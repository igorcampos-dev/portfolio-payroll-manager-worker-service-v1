package com.nexus.java.api.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdministratorLoginDto {
    @JsonProperty(namespace = "nome")
    String nome;
}
